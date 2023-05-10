package com.example.drugstore.repositories;

import com.example.drugstore.entities.ProductEntry;
import com.example.drugstore.enums.PharmacyNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductEntryRepository extends JpaRepository<ProductEntry, Integer> {
    List<ProductEntry> findByProductProductId(Integer productId);
    List<ProductEntry> findByPharmacyPharmacyId (Integer pharmacyId);
    ProductEntry findByPharmacyPharmacyNameAndProductProductId(PharmacyNameEnum pharmacyName, Integer productId);

}
