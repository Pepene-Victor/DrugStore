package com.example.drugstore.dto;

import java.util.Date;

public record ProductEntryPriceDTO(
        Integer productEntryPriceId,
        Integer productEntryId,
        Date date,
        Double price,
        Boolean inStock
) {
}
