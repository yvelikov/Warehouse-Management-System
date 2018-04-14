package org.softuni.wms.areas.parts.models.binding;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public class PartsDeliveryDto {

    @NotEmpty
    private String supplierId;

    @Valid
    private DeliveryPartDto[] parts;

    public PartsDeliveryDto() {
    }

    public PartsDeliveryDto(DeliveryPartDto[] parts) {
        this.parts = parts;
    }

    public String getSupplierId() {
        return this.supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public DeliveryPartDto[] getParts() {
        return this.parts;
    }

    public void setParts(DeliveryPartDto[] parts) {
        this.parts = parts;
    }
}
