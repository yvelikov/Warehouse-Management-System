package org.softuni.wms.areas.partners.models.view;

public class PartnerViewDto {

    private String id;
    private String name;
    private String vatNumber;
    private String address;
    private String phoneNumber;
    private Boolean isCustomer;
    private Boolean isSupplier;

    public PartnerViewDto() {
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

    public String getType(){
        if(this.isCustomer && this.isSupplier){
            return "Customer/Supplier";
        } else if(this.isCustomer){
            return "Customer";
        } else if(this.isSupplier){
            return "Supplier";
        } else return "Other";
    }
}
