package com.example.drugstore.controllers.interfaces;

import com.example.drugstore.dto.ProductDTO;
import com.example.drugstore.dto.ProductStatisticsDTO;
import com.example.drugstore.exceptions.NoProductEntryFound;
import com.example.drugstore.exceptions.NoProductFound;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "products")
public interface ProductControllerInterface {
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProductDTO> getAllProducts();
    @GetMapping(value = "/pharmacy/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProductDTO> getAllProductsByPharmacy(@PathVariable Integer pharmacyId);
    @GetMapping(value = "/statistics/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ProductStatisticsDTO getStatisticsForProduct(@PathVariable Integer productId) throws NoProductFound, NoProductEntryFound;
}
