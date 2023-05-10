package com.example.drugstore.controllers.interfaces;

import com.example.drugstore.dto.ProductEntryDTO;
import com.example.drugstore.exceptions.NoPharmacyFound;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "entries")
public interface ProductEntryControllerInterface {
    @GetMapping(value = "/product/{productId}")
    List<ProductEntryDTO> getProductEntryByProductId(@PathVariable Integer productId) ;

    @GetMapping(value = "/pharmacy/{pharmacyId}")
    List<ProductEntryDTO> getProductEntryByPharmacyId(@PathVariable Integer pharmacyId) throws NoPharmacyFound;


}
