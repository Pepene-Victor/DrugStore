package com.example.drugstore.controllers.impl;

import com.example.drugstore.controllers.interfaces.PharmacyControllerInterface;
import com.example.drugstore.dto.PharmacyDTO;
import com.example.drugstore.services.PharmacyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@AllArgsConstructor
@RestController
public class PharmacyController implements PharmacyControllerInterface {
    final private PharmacyService pharmacyService;
    @Override
    public List<PharmacyDTO> getAllPharmacies() {
        return pharmacyService.getAllPharmacies();
    }
}
