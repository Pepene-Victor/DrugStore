package com.example.drugstore.services;

import com.example.drugstore.dto.ProductEntryPriceDTO;
import com.example.drugstore.entities.Product;
import com.example.drugstore.entities.ProductEntry;
import com.example.drugstore.entities.ProductEntryPrice;
import com.example.drugstore.enums.PharmacyNameEnum;
import com.example.drugstore.exceptions.IdObjectIsNull;
import com.example.drugstore.exceptions.NoProductFound;
import com.example.drugstore.repositories.ProductEntryPriceRepository;
import com.example.drugstore.repositories.ProductEntryRepository;
import com.example.drugstore.exceptions.NoProductEntryFound;
import com.example.drugstore.repositories.ProductRepository;
import com.example.drugstore.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.drugstore.enums.PharmacyNameEnum.DRUGSTORE;

@Service
@Log4j2
@AllArgsConstructor
public class ProductEntryPriceService {

    final private ProductEntryPriceRepository entryPriceRepository;
    final private ProductEntryRepository entryRepository;
    final private ProductRepository productRepository;

    public List<ProductEntryPriceDTO> updateProductsPricesAutomatically() {
        List<Product> products = productRepository.findProductByPharmacyName(DRUGSTORE);
        products.forEach(product -> {
            try {
                updateProductPriceAutomaticallyNextDay(product.getProductId());
            } catch (NoProductFound e) {
                throw new RuntimeException(e);
            }
        });
        List<ProductEntryPrice> prices = entryPriceRepository.findLastPriceForProductInShop(DRUGSTORE);
        return productEntryPriceListToDTO(prices);
    }

    public ProductEntryPriceDTO updateProductPriceManually(ProductEntryPriceDTO entryPriceDTO) throws IdObjectIsNull {
        if (entryPriceDTO == null || entryPriceDTO.date() == null || entryPriceDTO.price() == null
        || entryPriceDTO.productEntryId() == null || entryPriceDTO.productEntryPriceId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Null fields found.");
        }
        ProductEntryPrice entryPrice = entryPriceRepository.getReferenceById(entryPriceDTO.productEntryPriceId());
        boolean sameDay = DateUtils.isSameDay(entryPrice.getDate(), entryPriceDTO.date());
        if (!sameDay || !Utils.todayOrNextDay(entryPriceDTO.date())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You cannot update in past.");
        }
        entryPrice.setPrice(entryPriceDTO.price());
        return convertToDTO(entryPriceRepository.save(entryPrice));
    }

    public ProductEntryPriceDTO insertProductPriceManually(ProductEntryPriceDTO entryPriceDTO) {
        if (entryPriceDTO == null || entryPriceDTO.date() == null || entryPriceDTO.price() == null
                || entryPriceDTO.productEntryId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Null fields found.");
        }
        ProductEntry entry = entryRepository.getReferenceById(entryPriceDTO.productEntryId());
        ProductEntryPrice entryPriceLast = entryPriceRepository.findLastPriceForEntry(entry.getProductEntryId());
        ProductEntryPrice entryPriceCurrent = entryPriceRepository.findCurrentPriceForEntry(entry.getProductEntryId());

        boolean sameDayLast = entryPriceLast !=null && DateUtils.isSameDay(entryPriceLast.getDate(), entryPriceDTO.date());
        boolean sameDayCurrent = entryPriceCurrent != null &&DateUtils.isSameDay(entryPriceCurrent.getDate(), entryPriceDTO.date());
        if (!Utils.todayOrNextDay(entryPriceDTO.date()) || sameDayLast || sameDayCurrent) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not allowed to insert.");
        }
        ProductEntryPrice newPrice = new ProductEntryPrice();
        newPrice.setPrice(entryPriceDTO.price());
        newPrice.setProductEntry(entry);
        newPrice.setDate(entryPriceDTO.date());
        newPrice.setInStock(true);
        return convertToDTO(entryPriceRepository.save(newPrice));
    }

    public ProductEntryPriceDTO updateProductPriceAutomaticallyNextDay(Integer productId) throws NoProductFound {
        var pricesOnPharmacies = getPricesForProduct(productId);
        Double newPrice = calculateMovingAverage(pricesOnPharmacies);
        ProductEntry entry = entryRepository.findByPharmacyPharmacyNameAndProductProductId(DRUGSTORE, productId);
        ProductEntryPrice entryPrice =  entryPriceRepository.findLastPriceForEntry(entry.getProductEntryId());

        Date nextDay = DateUtils.addDays(new Date(), 1);
        boolean sameDay = entryPrice != null && DateUtils.isSameDay(entryPrice.getDate(), nextDay);
        if (!sameDay || entryPrice == null) {
            ProductEntryPrice newEntryPrice = new ProductEntryPrice();
            newEntryPrice.setInStock(Boolean.TRUE);
            newEntryPrice.setDate(nextDay);
            newEntryPrice.setPrice(newPrice);
            newEntryPrice.setProductEntry(entry);
            return convertToDTO(entryPriceRepository.save(newEntryPrice));
        }
        entryPrice.setPrice(newPrice);
        return convertToDTO(entryPriceRepository.save(entryPrice));
    }

    public Double calculateMovingAverage(Map<PharmacyNameEnum, List<ProductEntryPrice>> map) {
        Map<PharmacyNameEnum, Double> averagesMap = new HashMap<>();
        for (Map.Entry<PharmacyNameEnum, List<ProductEntryPrice>> entry: map.entrySet()) {
            double priceTotal = entry.getValue().stream().mapToDouble(ProductEntryPrice::getPrice).sum();
            double movingAverage = priceTotal / entry.getValue().size();
            averagesMap.put(entry.getKey(), movingAverage);
        }
        Double newPrice = averagesMap.values().stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
        return roundToDecimals(newPrice, 2);
    }

    public Map<PharmacyNameEnum, List<ProductEntryPrice>> getPricesForProduct(Integer productId) throws NoProductFound {
        Map<PharmacyNameEnum, List<ProductEntryPrice>> priceList= new HashMap<>();
        List<ProductEntry> productEntries = entryRepository.findByProductProductId(productId)
                .stream().filter(productEntry -> !DRUGSTORE.equals(productEntry.getPharmacy().getPharmacyName()))
                .collect(Collectors.toList());
        productEntries.forEach(productEntry -> {
            List<ProductEntryPrice> entryPrices = entryPriceRepository.findLastWeekPricesForEntry(productEntry.getProductEntryId());
            priceList.put(productEntry.getPharmacy().getPharmacyName(), entryPrices);
        });
        return priceList;
    }

    public Map<PharmacyNameEnum, List<ProductEntryPriceDTO>> getPricesDTOForProduct(Integer productId) throws NoProductFound {
       var map = getPricesForProduct(productId);
        Map<PharmacyNameEnum, List<ProductEntryPriceDTO>> mapDTO = map.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(ProductEntryPriceService:: convertToDTO)
                                .collect(Collectors.toList())
                ));
        return mapDTO;
    }

    public ProductEntryPriceDTO getLastPriceByEntryId(Integer productEntryId) throws NoProductEntryFound {
        entryPriceExists(productEntryId);
        return convertToDTO(entryPriceRepository.findLastPriceForEntry(productEntryId));
    }

    public ProductEntryPriceDTO getCurrentPriceByEntryId(Integer productEntryId) throws NoProductEntryFound {
        entryPriceExists(productEntryId);
        return convertToDTO(entryPriceRepository.findCurrentPriceForEntry(productEntryId));
    }

    public List<ProductEntryPriceDTO> getLastWeekPricesByEntryId(Integer productEntryId) throws NoProductEntryFound {
        entryPriceExists(productEntryId);
        return productEntryPriceListToDTO(entryPriceRepository.findLastWeekPricesForEntry(productEntryId));
    }

    public List<ProductEntryPriceDTO> getPricesByEntryId(Integer productEntryId) throws NoProductEntryFound {
        entryPriceExists(productEntryId);
        return productEntryPriceListToDTO(entryPriceRepository.findByProductEntryProductEntryId(productEntryId));
    }

    public static ProductEntryPriceDTO convertToDTO(ProductEntryPrice entryPrice) {
        ProductEntryPriceDTO entryPriceDTO = new ProductEntryPriceDTO(
                entryPrice.getProductEntryPriceId(),
                entryPrice.getProductEntry().getProductEntryId(),
                entryPrice.getDate(),
                entryPrice.getPrice(),
                entryPrice.getInStock()
        );
        return entryPriceDTO;
    }

    private List<ProductEntryPriceDTO> productEntryPriceListToDTO(List<ProductEntryPrice> entryPrices) {
        List<ProductEntryPriceDTO> entryPriceDTOS = entryPrices.stream().map(entryPrice ->
                convertToDTO(entryPrice)
        ).collect(Collectors.toList());
        return entryPriceDTOS;
    }

    public void entryPriceExists(Integer productEntryId) throws NoProductEntryFound {
        boolean exists = entryRepository.existsById(productEntryId);
        if (!exists) {
            log.error("No product entry found with id: " + productEntryId);
            throw new NoProductEntryFound(productEntryId);
        }
    }

    private double roundToDecimals(double price, int scale) {
        BigDecimal bd = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();

    }
}
