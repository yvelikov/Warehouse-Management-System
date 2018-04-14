package org.softuni.wms.areas.partners.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EditPartnerDto {

    private String id;

    @NotEmpty(message = "Partner name cannot be empty")
    @Size(min = 5, max = 30, message = "Partner name must be between 5 and 30 symbols long")
    private String name;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Please enter valid VAT number.")
    private String vatNumber;

    @Size(max = 30, message = "Address must be less than 30 symbols long.")
    private String address;

    @Pattern(regexp = "(^$|[0-9]{10,14})", message = "Please enter valid phone number between 10 and 14 symbols.\n Example: 0123456789")
    private String phoneNumber;
    private Boolean isCustomer;
    private Boolean isSupplier;

    public EditPartnerDto() {
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

    public Boolean getCustomer() {
        return this.isCustomer;
    }

    public void setCustomer(Boolean customer) {
        isCustomer = customer;
    }

    public Boolean getSupplier() {
        return this.isSupplier;
    }

    public void setSupplier(Boolean supplier) {
        isSupplier = supplier;
    }
}
