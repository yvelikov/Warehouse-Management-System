package org.softuni.wms.areas.documents.models.service;

import org.softuni.wms.areas.partners.models.service.PartnerServiceDto;
import org.softuni.wms.areas.users.models.service.UserServiceDto;

import java.time.LocalDate;

public class DocumentServiceDto {

    private String id;
    private Long documentNumber;
    private LocalDate date;
    private UserServiceDto user;
    private PartnerServiceDto partner;

    public DocumentServiceDto() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getDocumentNumber() {
        return this.documentNumber;
    }

    public void setDocumentNumber(Long documentNumber) {
        this.documentNumber = documentNumber;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public UserServiceDto getUser() {
        return this.user;
    }

    public void setUser(UserServiceDto user) {
        this.user = user;
    }

    public PartnerServiceDto getPartner() {
        return this.partner;
    }

    public void setPartner(PartnerServiceDto partner) {
        this.partner = partner;
    }
}
