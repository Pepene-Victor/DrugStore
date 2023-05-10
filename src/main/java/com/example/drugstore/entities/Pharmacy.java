package com.example.drugstore.entities;

import com.example.drugstore.enums.PharmacyNameEnum;
import jakarta.persistence.*;
import lombok.ToString;

import java.util.Objects;

@ToString
@Entity
@Table(name = "pharmacies")
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pharmacy-generator")
    @SequenceGenerator(name = "pharmacy-generator", sequenceName = "pharmacies_pharmacy_id_seq", allocationSize = 1)

    @Column(name = "pharmacy_id")
    private Integer pharmacyId;

    @Column(name = "pharmacy_name")
    @Enumerated(EnumType.STRING)
    private PharmacyNameEnum pharmacyName;

    public Integer getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Integer pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public PharmacyNameEnum getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(PharmacyNameEnum pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pharmacy pharmacy)) return false;
        return Objects.equals(getPharmacyId(), pharmacy.getPharmacyId()) && getPharmacyName() == pharmacy.getPharmacyName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPharmacyId(), getPharmacyName());
    }

}
