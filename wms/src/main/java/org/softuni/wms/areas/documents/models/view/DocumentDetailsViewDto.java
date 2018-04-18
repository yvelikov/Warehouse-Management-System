package org.softuni.wms.areas.documents.models.view;

import java.time.LocalDate;

public class DocumentDetailsViewDto {

    private String id;
    private String type;
    private String documentCode;
    private LocalDate date;
    private String user;
    private String partner;
    private String partnerVatNumber;
    private String partnerAddress;

    public DocumentDetailsViewDto() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type.charAt(0) + type.substring(1).toLowerCase() + " note";
    }

    public String getDocumentCode() {
        return this.documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPartner() {
        return this.partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getPartnerVatNumber() {
        return this.partnerVatNumber;
    }

    public void setPartnerVatNumber(String partnerVatNumber) {
        this.partnerVatNumber = partnerVatNumber;
    }

    public String getPartnerAddress() {
        return this.partnerAddress;
    }

    public void setPartnerAddress(String partnerAddress) {
        this.partnerAddress = partnerAddress;
    }
}
