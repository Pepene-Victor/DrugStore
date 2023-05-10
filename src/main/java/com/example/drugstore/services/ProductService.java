package com.example.drugstore.services;

import com.example.drugstore.dto.ProductDTO;
import com.example.drugstore.dto.ProductEntryPriceDTO;
import com.example.drugstore.dto.ProductStatisticsDTO;
import com.example.drugstore.entities.Product;
import com.example.drugstore.entities.ProductEntry;
import com.example.drugstore.entities.ProductEntryPrice;
import com.example.drugstore.enums.PharmacyNameEnum;
import com.example.drugstore.exceptions.NoProductEntryFound;
import com.example.drugstore.exceptions.NoProductFound;
import com.example.drugstore.repositories.ProductEntryPriceRepository;
import com.example.drugstore.repositories.ProductEntryRepository;
import com.example.drugstore.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductEntryPriceService priceService;
    private final ProductEntryRepository entryRepository;

    public ProductStatisticsDTO getStatisticsForProduct(Integer productId) throws NoProductFound, NoProductEntryFound {
        productExists(productId);
        ProductDTO productDTO = convertToDTO(productRepository.getReferenceById(productId));
        ProductEntry productEntry = entryRepository.findByPharmacyPharmacyNameAndProductProductId(PharmacyNameEnum.DRUGSTORE, productId);
        List<ProductEntryPriceDTO> lastWeekPrices = priceService.getLastWeekPricesByEntryId(productEntry.getProductEntryId());
        var map = priceService.getPricesDTOForProduct(productId);
        ProductStatisticsDTO statisticsDTO = new ProductStatisticsDTO(
                productDTO,
                lastWeekPrices,
                map
        );
        return statisticsDTO;
    }
    public List<ProductDTO> getAllProducts (){
        List<Product> productList = productRepository.findAll();
        return convertToDTOList(productList);
    }

    public List<ProductDTO> getAllProductsByPharmacyId(Integer pharmacyId) {
        List<Product> productList = productRepository.findProductByPharmacyId(pharmacyId);
        return convertToDTOList(productList);
    }

    public ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO(
                product.getProductId(),
                product.getCatalogName(),
                product.getCatalog()
        );
        return productDTO;
    }

    private List<ProductDTO> convertToDTOList(List<Product> productDTOList) {
        return productDTOList.stream().map(product -> convertToDTO(product)).collect(Collectors.toList());
    }

    public void productExists(Integer productId) {
        boolean exists = productRepository.existsById(productId);
        if (!exists) {
            log.error("No product found with id:" + productId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No product found with id:" + productId);
        }
    }
}
