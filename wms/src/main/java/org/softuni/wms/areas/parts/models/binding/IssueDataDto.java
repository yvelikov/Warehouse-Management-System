package org.softuni.wms.areas.parts.models.binding;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class IssueDataDto implements OperationData{

    private static final String ROWS_ERROR = "You need at least one row to execute an issue.";
    private static final String CUSTOMER_ERROR = "You need to select a customer.";

    @NotNull(message = CUSTOMER_ERROR)
    private String customerId;

    @NotNull(message = ROWS_ERROR)
    @Min(value = 1, message = ROWS_ERROR)
    private Integer numberOfRows;

    public IssueDataDto() {
    }

    @Override
    public String getPartnerId() {
        return this.customerId;
    }

    @Override
    public void setPartnerId(String partnerId) {
        this.customerId = partnerId;
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
