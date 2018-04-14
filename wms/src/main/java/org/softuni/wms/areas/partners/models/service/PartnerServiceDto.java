package org.softuni.wms.areas.partners.models.service;

public class PartnerServiceDto {

    private String id;
    private String name;
    private String vatNumber;
    private String address;
    private String phoneNumber;
    private boolean isCustomer;
    private boolean isSupplier;

    public PartnerServiceDto() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVatNumber() {
        return this.vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isCustomer() {
        return this.isCustomer;
    }

    public void setCustomer(boolean customer) {
        isCustomer = customer;
    }

    public boolean isSupplier() {
        return this.isSupplier;
    }

    public void setSupplier(boolean supplier) {
        isSupplier = supplier;
    }
}
