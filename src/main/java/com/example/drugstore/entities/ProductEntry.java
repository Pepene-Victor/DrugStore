package com.example.drugstore.entities;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "product_entry")
public class ProductEntry {
    @Id
    @Column(name = "product_entry_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product-entry-generator")
    @SequenceGenerator(name = "product-entry-generator", sequenceName = "product_entry_product_entry_id_seq", allocationSize = 1)
    private Integer productEntryId;
    @ManyToOne
    @JoinColumn(name = "catalog_id", nullable = false)
    private Product product;
    @OneToOne
    @JoinColumn(name = "pharmacy_id", referencedColumnName = "pharmacy_id")
    private Pharmacy pharmacy;
    @Column(name = "pharmacy_product_name")
    private String pharmacyProductName;
    @Column(name = "pharmacy_product_link")
    private String pharmacyProductLink;
    @OneToMany(mappedBy = "productEntry")
    private Set<ProductEntryPrice> productEntryPrices;

    public Integer getProductEntryId() {
        return productEntryId;
    }

    public void setProductEntryId(Integer productEntryId) {
        this.productEntryId = productEntryId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public String getPharmacyProductName() {
        return pharmacyProductName;
    }

    public void setPharmacyProductName(String pharmacyProductName) {
        this.pharmacyProductName = pharmacyProductName;
    }

    public String getPharmacyProductLink() {
        return pharmacyProductLink;
    }

    public void setPharmacyProductLink(String pharmacyProductLink) {
        this.pharmacyProductLink = pharmacyProductLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductEntry that)) return false;
        return Objects.equals(getProductEntryId(), that.getProductEntryId()) && Objects.equals(getProduct(), that.getProduct()) && Objects.equals(getPharmacy(), that.getPharmacy()) && Objects.equals(getPharmacyProductName(), that.getPharmacyProductName()) && Objects.equals(getPharmacyProductLink(), that.getPharmacyProductLink());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductEntryId(), getProduct(), getPharmacy(), getPharmacyProductName(), getPharmacyProductLink());
    }

    @Override
    public String toString() {
        return "ProductEntry{" +
                "productEntryId=" + productEntryId +
                ", product=" + product +
                ", pharmacy=" + pharmacy +
                ", pharmacyProductName='" + pharmacyProductName + '\'' +
                ", pharmacyProductLink='" + pharmacyProductLink + '\'' +
                '}';
    }
}
