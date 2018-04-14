package org.softuni.wms.areas.parts.models.binding;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class DeliveryDataDto {

    @NotNull(message = "You need to select a supplier.")
    private String supplierId;
    @NotNull(message = "You need at least one row to execute a delivery.")
    @Min(value = 1, message = "You need at least one row to execute a delivery.")
    private Integer numberOfRows;

    public DeliveryDataDto() {
    }

    public String getSupplierId() {
        return this.supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getNumberOfRows() {
        return this.numberOfRows;
    }

    public void setNumberOfRows(Integer numberOfRows) {
        this.numberOfRows = numberOfRows;
    }
}
