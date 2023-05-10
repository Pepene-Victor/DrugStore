package com.example.drugstore.services;

import com.example.drugstore.dto.PharmacyDTO;
import com.example.drugstore.entities.Pharmacy;
import com.example.drugstore.exceptions.NoPharmacyFound;
import com.example.drugstore.repositories.PharmacyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class PharmacyService {
    private final PharmacyRepository pharmacyRepository;

    public PharmacyDTO convertToDTO(Pharmacy pharmacy) {
        PharmacyDTO pharmacyDTO = new PharmacyDTO(
                pharmacy.getPharmacyId(),
                pharmacy.getPharmacyName()
        );
        return pharmacyDTO;
    }

    public List<PharmacyDTO> getAllPharmacies(){
        List<PharmacyDTO> pharmacies = pharmacyRepository.findAll().stream()
                .map( pharmacy -> convertToDTO(pharmacy)).collect(Collectors.toList());
        return pharmacies;
    }

    public void pharmacyExists(Integer pharmacyId) throws NoPharmacyFound {
        boolean exists = pharmacyRepository.existsById(pharmacyId);
        if (!exists) {
            log.error("No pharmacy found with id: " + pharmacyId);
            throw new NoPharmacyFound(pharmacyId);
        }
    }
}
