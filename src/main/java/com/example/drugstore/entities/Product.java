package com.example.drugstore.entities;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product-generator")
    @SequenceGenerator(name = "product-generator", sequenceName = "products_catalog_id_seq", allocationSize = 1)
    @Column(name = "catalog_id")
    private Integer productId;
    @Column(name = "catalog_name", nullable = false)
    private String catalogName;
    @Column(name = "catalog", nullable = false)
    private String catalog;
    @OneToMany(mappedBy = "product")
    private Set<ProductEntry> productEntries;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public Set<ProductEntry> getProductEntries() {
        return productEntries;
    }

    public void setProductEntries(Set<ProductEntry> productEntries) {
        this.productEntries = productEntries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(getProductId(), product.getProductId()) && Objects.equals(getCatalogName(), product.getCatalogName()) && Objects.equals(getCatalog(), product.getCatalog()) && Objects.equals(getProductEntries(), product.getProductEntries());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getCatalogName(), getCatalog(), getProductEntries());
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_id=" + productId +
                ", catalog_name='" + catalogName + '\'' +
                ", catalog='" + catalog + '\'' +
                ", productEntries=" + productEntries +
                '}';
    }
}
