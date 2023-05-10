package com.example.drugstore.controllers.impl;

import com.example.drugstore.controllers.interfaces.ProductControllerInterface;
import com.example.drugstore.dto.ProductDTO;
import com.example.drugstore.dto.ProductStatisticsDTO;
import com.example.drugstore.exceptions.NoProductEntryFound;
import com.example.drugstore.exceptions.NoProductFound;
import com.example.drugstore.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProductController implements ProductControllerInterface {
    final private ProductService productService;

    @Override
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @Override
    public List<ProductDTO> getAllProductsByPharmacy(Integer pharmacyId) {
        return productService.getAllProductsByPharmacyId(pharmacyId);
    }

    @Override
    public ProductStatisticsDTO getStatisticsForProduct(Integer productId) throws NoProductFound, NoProductEntryFound {
        return productService.getStatisticsForProduct(productId);
    }

}
