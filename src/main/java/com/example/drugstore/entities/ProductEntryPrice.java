package com.example.drugstore.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "product_entry_price")
public class ProductEntryPrice {
    @Id
    @Column(name = "product_entry_price_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "price-com-generator")
    @SequenceGenerator(name = "price-com-generator", sequenceName = "product_entry_price_product_entry_price_id_seq", allocationSize = 1)
    private Integer productEntryPriceId;
    @ManyToOne
    @JoinColumn(name = "product_entry_id", nullable = false)
    private ProductEntry productEntry;
    @Column(name = "day")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "price")
    private Double price;
    @Column(name = "in_stock")
    private Boolean inStock;

    public Integer getProductEntryPriceId() {
        return productEntryPriceId;
    }

    public void setProductEntryPriceId(Integer productEntryPriceId) {
        this.productEntryPriceId = productEntryPriceId;
    }

    public ProductEntry getProductEntry() {
        return productEntry;
    }

    public void setProductEntry(ProductEntry productEntry) {
        this.productEntry = productEntry;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductEntryPrice that)) return false;
        return Objects.equals(getProductEntryPriceId(), that.getProductEntryPriceId()) && Objects.equals(getProductEntry(), that.getProductEntry()) && Objects.equals(getDate(), that.getDate()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getInStock(), that.getInStock());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductEntryPriceId(), getProductEntry(), getDate(), getPrice(), getInStock());
    }

    @Override
    public String toString() {
        return "ProductEntryPrice{" +
                "productEntryId=" + productEntryPriceId +
                ", productEntry=" + productEntry +
                ", date=" + date +
                ", price=" + price +
                ", inStock=" + inStock +
                '}';
    }
}
