package org.softuni.wms.areas.parts.models.view;

import org.softuni.wms.areas.parts.entities.enums.UnitOfMeasure;

import java.math.BigDecimal;

public class PartViewDto {

    private String id;
    private String articleCode;
    private String name;
    private BigDecimal listPrice;
    private Long quantity;
    private UnitOfMeasure unitOfMeasure;

    public PartViewDto() {
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

    public BigDecimal getListPrice() {
        return this.listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
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
}
