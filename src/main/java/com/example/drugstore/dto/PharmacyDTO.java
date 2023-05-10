package com.example.drugstore.dto;

import com.example.drugstore.enums.PharmacyNameEnum;

public record PharmacyDTO(
        Integer pharmacyId,
        PharmacyNameEnum pharmacyName
) {
}
