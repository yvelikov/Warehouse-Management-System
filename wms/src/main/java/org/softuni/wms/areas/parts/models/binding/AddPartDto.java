package org.softuni.wms.areas.parts.models.binding;

import org.softuni.wms.areas.parts.entities.enums.UnitOfMeasure;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class AddPartDto {

    @NotEmpty(message = "SKU code cannot be empty.")
    @Pattern(regexp = "(^$|[0-9]{5,10})", message = "SKU code must be between 5 an 10 symbols long ")
    private String articleCode;

    @NotEmpty(message = "Part name cannot be empty.")
    @Size(min = 4, max = 32, message = "Part name must be between 4 and 32 symbols long")
    private String name;

    @Min(value = 0,message = "Delivery price cannot be negative number.")
    private BigDecimal deliveryPrice;

    @Min(value = 0,message = "List price cannot be negative number.")
    private BigDecimal listPrice;

    @Min(value = 0, message = "Mark up cannot be negative number.")
    @Max(value = 300,message = "Mark up cannot be more than 300")
    private Double markUp;

    @NotNull(message = "Unit of measure cannot be empty.")
    private UnitOfMeasure unitOfMeasure;

    @NotEmpty(message = "Supplier cannot be empty.")
    private String supplier;

    public AddPartDto() {
    }

    public String getArticleCode() {
        return this.articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getDeliveryPrice() {
        return this.deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public BigDecimal getListPrice() {
        return this.listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public Double getMarkUp() {
        return this.markUp;
    }

    public void setMarkUp(Double markUp) {
        this.markUp = markUp;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return this.unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getSupplier() {
        return this.supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}
