package org.softuni.wms.areas.documents.models.view;

import java.time.LocalDate;

public class DocumentViewDto {

    private String id;
    private String documentCode;
    private LocalDate date;
    private String user;
    private String partner;

    public DocumentViewDto() {
    }

    public DocumentViewDto(String id, String documentCode, LocalDate date, String user, String partner) {
        this.id = id;
        this.documentCode = documentCode;
        this.date = date;
        this.user = user;
        this.partner = partner;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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
}
