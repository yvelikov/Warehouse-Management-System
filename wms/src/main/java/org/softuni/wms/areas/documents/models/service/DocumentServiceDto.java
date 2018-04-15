package org.softuni.wms.areas.documents.models.service;

import org.softuni.wms.areas.partners.entities.Partner;
import org.softuni.wms.areas.users.entities.User;

import java.time.LocalDate;

public class DocumentServiceDto {

    private String id;
    private Long documentNumber;
    private LocalDate date;
    private User user;
    private Partner partner;

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

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Partner getPartner() {
        return this.partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }
}
