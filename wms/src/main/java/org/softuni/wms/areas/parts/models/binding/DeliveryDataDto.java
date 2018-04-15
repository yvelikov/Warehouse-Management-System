package org.softuni.wms.areas.parts.models.binding;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class DeliveryDataDto implements OperationData{

    @NotNull(message = "You need to select a supplier.")
    private String supplierId;
    @NotNull(message = "You need at least one row to execute a delivery.")
    @Min(value = 1, message = "You need at least one row to execute a delivery.")
    private Integer numberOfRows;

    public DeliveryDataDto() {
    }


    @Override
    public String getPartnerId() {
        return this.supplierId;
    }

    @Override
    public void setPartnerId(String partnerId) {
        this.supplierId = partnerId;
    }

    @Override
    public Integer getNumberOfRows() {
        return this.numberOfRows;
    }

    @Override
    public void setNumberOfRows(Integer numberOfRows) {
        this.numberOfRows = numberOfRows;
    }
}
