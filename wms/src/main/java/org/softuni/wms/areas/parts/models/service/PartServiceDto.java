package org.softuni.wms.areas.parts.models.service;

import org.softuni.wms.areas.partners.entities.Partner;
import org.softuni.wms.areas.parts.entities.enums.UnitOfMeasure;

import java.math.BigDecimal;

public class PartServiceDto {
    private String id;
    private String articleCode;
    private String name;
    private BigDecimal deliveryPrice;
    private BigDecimal listPrice;
    private Double markUp;
    private Long quantity;
    private UnitOfMeasure unitOfMeasure;
    private Partner supplier;

    public PartServiceDto() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return this.unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Partner getSupplier() {
        return this.supplier;
    }

    public void setSupplier(Partner supplier) {
        this.supplier = supplier;
    }
}
