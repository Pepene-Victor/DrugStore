package com.example.drugstore.controllers.impl;

import com.example.drugstore.controllers.interfaces.ProductEntryControllerInterface;
import com.example.drugstore.dto.ProductEntryDTO;
import com.example.drugstore.exceptions.NoPharmacyFound;
import com.example.drugstore.services.ProductEntryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProductEntryController implements ProductEntryControllerInterface {

    final private ProductEntryService entryService;
    @Override
    public List<ProductEntryDTO> getProductEntryByProductId(Integer productId) {
        return entryService.productEntriesDTOByProductId(productId);
    }

    @Override
    public List<ProductEntryDTO> getProductEntryByPharmacyId(Integer pharmacyId) throws NoPharmacyFound {
        return entryService.productEntriesDTOByPharmacyId(pharmacyId);
    }
}
