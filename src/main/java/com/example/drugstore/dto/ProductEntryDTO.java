package com.example.drugstore.dto;

public record ProductEntryDTO(
        Integer productEntryId,
        ProductDTO productDTO,
        PharmacyDTO pharmacyDTO,
        String pharmacyProductName,
        String pharmacyProductLink
) {
}
