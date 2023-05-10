package com.example.drugstore.repositories;

import com.example.drugstore.entities.Product;
import com.example.drugstore.enums.PharmacyNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT distinct pp.* FROM product_entry pe inner join products pp " +
            "on pp.catalog_id = pe.catalog_id where pe.pharmacy_id = ?1 order by pp.catalog_id",
            nativeQuery = true)
    List<Product> findProductByPharmacyId(Integer pharmacyId);

    @Query(value = "SELECT p from Product p inner join ProductEntry pe inner join Pharmacy ph where ph.pharmacyName = ?1",
            nativeQuery = false)
    List<Product> findProductByPharmacyName(PharmacyNameEnum pharmacyNameEnum);
}
