package com.example.drugstore.controllers.impl;

import com.example.drugstore.controllers.interfaces.ProductEntryPriceControllerInterface;
import com.example.drugstore.dto.ProductEntryPriceDTO;
import com.example.drugstore.exceptions.IdObjectIsNull;
import com.example.drugstore.exceptions.NoProductEntryFound;
import com.example.drugstore.exceptions.NoProductFound;
import com.example.drugstore.services.ProductEntryPriceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProductEntryPriceController implements ProductEntryPriceControllerInterface {

    final private ProductEntryPriceService priceService;

    @Override
    public List<ProductEntryPriceDTO> getPricesByEntryId(Integer productEntryId) throws NoProductEntryFound {
        return priceService.getPricesByEntryId(productEntryId);
    }

    @Override
    public ProductEntryPriceDTO getLastPriceByEntryId(Integer productEntryId) throws NoProductEntryFound {
        return priceService.getLastPriceByEntryId(productEntryId);
    }

    @Override
    public ProductEntryPriceDTO getCurrentPriceByEntryId(Integer productEntryId) throws NoProductEntryFound {
        return priceService.getCurrentPriceByEntryId(productEntryId);
    }

    @Override
    public List<ProductEntryPriceDTO> getLastWeekPricesByEntryId(Integer productEntryId) throws NoProductEntryFound {
        return priceService.getLastWeekPricesByEntryId(productEntryId);
    }

    @Override
    public ProductEntryPriceDTO updateProductPriceAutomaticallyNextDay(Integer productId) throws NoProductFound {
        return priceService.updateProductPriceAutomaticallyNextDay(productId);
    }

    @Override
    public List<ProductEntryPriceDTO> updateProductsPricesAutomatically() {
        return priceService.updateProductsPricesAutomatically();
    }

    @Override
    public ProductEntryPriceDTO updateProductPriceManually(ProductEntryPriceDTO entryPriceDTO) throws IdObjectIsNull {
        return priceService.updateProductPriceManually(entryPriceDTO);
    }

    @Override
    public ProductEntryPriceDTO insertProductPriceManually(ProductEntryPriceDTO entryPriceDTO) {
        return priceService.insertProductPriceManually(entryPriceDTO);
    }
}
