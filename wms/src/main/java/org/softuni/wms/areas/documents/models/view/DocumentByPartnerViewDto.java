package org.softuni.wms.areas.documents.models.view;

import java.time.LocalDate;

public class DocumentByPartnerViewDto {

    private String id;
    private String documentCode;
    private LocalDate date;
    private String type;

    public DocumentByPartnerViewDto() {
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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
