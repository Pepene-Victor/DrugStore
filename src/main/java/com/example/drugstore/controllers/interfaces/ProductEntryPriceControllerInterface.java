package com.example.drugstore.controllers.interfaces;

import com.example.drugstore.dto.ProductEntryPriceDTO;
import com.example.drugstore.exceptions.IdObjectIsNull;
import com.example.drugstore.exceptions.NoProductEntryFound;
import com.example.drugstore.exceptions.NoProductFound;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "prices")
public interface ProductEntryPriceControllerInterface {
    @GetMapping(value = "/get/{productEntryId}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProductEntryPriceDTO> getPricesByEntryId(@PathVariable Integer productEntryId) throws NoProductEntryFound;

    @GetMapping(value = "/get/last-price/{productEntryId}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    ProductEntryPriceDTO getLastPriceByEntryId(@PathVariable Integer productEntryId) throws NoProductEntryFound;

    @GetMapping(value = "/get/current-price/{productEntryId}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    ProductEntryPriceDTO getCurrentPriceByEntryId(@PathVariable Integer productEntryId) throws NoProductEntryFound;

    @GetMapping(value = "/get/last-week-price/{productEntryId}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProductEntryPriceDTO> getLastWeekPricesByEntryId(@PathVariable Integer productEntryId) throws NoProductEntryFound;

    @PostMapping(value = "/update-automatically/product-price/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ProductEntryPriceDTO updateProductPriceAutomaticallyNextDay(@PathVariable Integer productId) throws NoProductFound;

    @PostMapping(value = "/update-automatically/products-prices", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProductEntryPriceDTO> updateProductsPricesAutomatically();

    @PutMapping(value = "/update-manual/product-price", produces = MediaType.APPLICATION_JSON_VALUE)
    ProductEntryPriceDTO updateProductPriceManually( @RequestBody ProductEntryPriceDTO entryPriceDTO) throws IdObjectIsNull;

    @PutMapping(value = "/insert-manual/product-price", produces = MediaType.APPLICATION_JSON_VALUE)
    ProductEntryPriceDTO insertProductPriceManually( @RequestBody ProductEntryPriceDTO entryPriceDTO);
}
