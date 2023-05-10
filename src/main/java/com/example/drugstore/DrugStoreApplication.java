package com.example.drugstore;

import com.example.drugstore.repositories.ProductEntryPriceRepository;
import com.example.drugstore.repositories.ProductRepository;
import com.example.drugstore.services.ProductEntryPriceService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class DrugStoreApplication {
    final private ProductEntryPriceService priceService;
    final private ProductRepository productRepository;
    final private ProductEntryPriceRepository entryPriceRepository;
    public static void main(String[] args) {
        SpringApplication.run(DrugStoreApplication.class, args);
    }

    @PostConstruct
    void postConstruct()  {

    }
}
