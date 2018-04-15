package org.softuni.wms.areas.parts.models.binding;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public class PartsOperationDto {

    @NotEmpty
    private String partnerId;

    @Valid
    private OperationPartDto[] parts;

    public PartsOperationDto() {
    }

    public PartsOperationDto(OperationPartDto[] parts) {
        this.parts = parts;
    }

    public String getPartnerId() {
        return this.partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public OperationPartDto[] getParts() {
        return this.parts;
    }

    public void setParts(OperationPartDto[] parts) {
        this.parts = parts;
    }
}
