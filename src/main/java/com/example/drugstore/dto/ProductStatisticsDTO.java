package com.example.drugstore.dto;

import com.example.drugstore.enums.PharmacyNameEnum;

import java.util.List;
import java.util.Map;

public record ProductStatisticsDTO(
        ProductDTO productDTO,
        List<ProductEntryPriceDTO> lastWeekPrices,
        Map<PharmacyNameEnum, List<ProductEntryPriceDTO>> competitorPrices
) {

}
