package com.example.drugstore.controllers.interfaces;

import com.example.drugstore.dto.PharmacyDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "pharmacies")
public interface PharmacyControllerInterface {
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<PharmacyDTO> getAllPharmacies();
}
