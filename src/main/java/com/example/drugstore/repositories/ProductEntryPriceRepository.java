package com.example.drugstore.repositories;

import com.example.drugstore.entities.ProductEntryPrice;
import com.example.drugstore.enums.PharmacyNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductEntryPriceRepository extends JpaRepository<ProductEntryPrice, Integer> {
    List<ProductEntryPrice> findByProductEntryProductEntryId(Integer productEntryId);
    @Query(value = "SELECT * FROM product_entry_price pep where pep.product_entry_id =?1 " +
            "order by pep.day desc LIMIT 1",
            nativeQuery = true)
    ProductEntryPrice findLastPriceForEntry(Integer productEntryId);

    @Query(value = "SELECT * FROM product_entry_price pep where pep.product_entry_id =?1 " +
            " and pep.day = current_date order by pep.day desc LIMIT 1",
            nativeQuery = true)
    ProductEntryPrice findCurrentPriceForEntry(Integer productEntryId);

    @Query(value = "SELECT * FROM product_entry_price pep where pep.product_entry_id =?1 " +
            "order by pep.day desc LIMIT 7",
            nativeQuery = true)
    List<ProductEntryPrice> findLastWeekPricesForEntry(Integer productEntryId);

    @Query(value = "SELECT pep FROM ProductEntryPrice pep  " +
            " left join ProductEntryPrice pep2 on pep.productEntry.productEntryId = pep2.productEntry.productEntryId " +
            " and pep.date < pep2.date where pep.productEntry.pharmacy.pharmacyName = ?1 and pep2.productEntry.productEntryId is null ")
    List<ProductEntryPrice> findLastPriceForProductInShop(PharmacyNameEnum pharmacyNameEnum);

    @Query(value = "SELECT pep from ProductEntryPrice pep where pep.productEntry.productEntryId = ?1 and pep.productEntryPriceId = ?2")
    ProductEntryPrice findByEntryIdAndPriceId(Integer productEntryId, Integer productEntryPriceId);


}
