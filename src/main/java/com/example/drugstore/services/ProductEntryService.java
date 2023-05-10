package com.example.drugstore.services;

import com.example.drugstore.dto.ProductEntryDTO;
import com.example.drugstore.entities.ProductEntry;
import com.example.drugstore.enums.PharmacyNameEnum;
import com.example.drugstore.exceptions.NoPharmacyFound;
import com.example.drugstore.repositories.ProductEntryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductEntryService {
    final private ProductEntryRepository productEntryRepository;
    final private PharmacyService pharmacyService;
    final private ProductService productService;
    final private ProductEntryRepository entryRepository;

    public List<ProductEntryDTO> productEntriesDTOByProductId(Integer productId) {
        productService.productExists(productId);
        List<ProductEntry> productEntries = productEntryRepository.findByProductProductId(productId);
        return productEntryListToDTO(productEntries);
    }

    public List<ProductEntryDTO> productEntriesDTOByPharmacyId(Integer pharmacyId) throws NoPharmacyFound {
        pharmacyService.pharmacyExists(pharmacyId);
        List<ProductEntry> productEntries = productEntryRepository.findByPharmacyPharmacyId(pharmacyId);
        return productEntryListToDTO(productEntries);
    }

    public ProductEntry productEntryForOurPharmacy(Integer productId) {
        ProductEntry entry = entryRepository.findByPharmacyPharmacyNameAndProductProductId(PharmacyNameEnum.DRUGSTORE, productId);
        return entry;
    }


    private List<ProductEntryDTO> productEntryListToDTO(List<ProductEntry> productEntries) {
        List<ProductEntryDTO> entryDTOS = productEntries.stream().map(productEntry ->
            convertToDTO(productEntry)
        ).collect(Collectors.toList());
        return entryDTOS;
    }

    private ProductEntryDTO convertToDTO(ProductEntry productEntry) {
        ProductEntryDTO productEntryDTO = new ProductEntryDTO(
                productEntry.getProductEntryId(),
                productService.convertToDTO(productEntry.getProduct()),
                pharmacyService.convertToDTO(productEntry.getPharmacy()),
                productEntry.getPharmacyProductName(),
                productEntry.getPharmacyProductLink()
        );
        return productEntryDTO;
    }
}
