package org.softuni.wms.areas.parts.models.binding;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class DeliveryDataDto implements OperationData{

    private static final String CUSTOMER_ERROR = "You need to select a supplier.";
    private static final String ROWS_ERROR = "You need at least one row to execute a delivery.";

    @NotNull(message = CUSTOMER_ERROR)
    private String supplierId;

    @NotNull(message = ROWS_ERROR)
    @Min(value = 1, message = ROWS_ERROR)
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
