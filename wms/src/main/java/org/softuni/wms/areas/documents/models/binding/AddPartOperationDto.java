package org.softuni.wms.areas.documents.models.binding;

import org.softuni.wms.areas.documents.models.service.DocumentServiceDto;

public class AddPartOperationDto {

    private DocumentServiceDto document;
    private String partId;
    private Long quantity;
    private String type;

    public AddPartOperationDto() {
    }

    public DocumentServiceDto getDocument() {
        return this.document;
    }

    public void setDocument(DocumentServiceDto document) {
        this.document = document;
    }

    public String getPartId() {
        return this.partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
