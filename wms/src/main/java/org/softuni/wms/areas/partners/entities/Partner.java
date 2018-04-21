package org.softuni.wms.areas.partners.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "partners")
public class Partner {

    private String id;
    private String name;
    private String vatNumber;
    private String address;
    private String phoneNumber;
    private boolean isCustomer;
    private boolean isSupplier;

    public Partner() {
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false, updatable = false)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(nullable = false, unique = true)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false, unique = true)
    public String getVatNumber() {
        return this.vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    @Column
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "is_customer", nullable = false)
    public boolean getCustomer() {
        return this.isCustomer;
    }

    public void setCustomer(boolean customer) {
        this.isCustomer = customer;
    }

    @Column(name = "is_supplier",nullable = false)
    public boolean getSupplier() {
        return this.isSupplier;
    }

    public void setSupplier(boolean supplier) {
        this.isSupplier = supplier;
    }
}
