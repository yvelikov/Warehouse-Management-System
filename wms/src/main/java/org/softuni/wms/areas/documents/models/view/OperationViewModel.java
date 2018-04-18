package org.softuni.wms.areas.documents.models.view;

import java.math.BigInteger;

public class OperationViewModel {

    private String articleCode;
    private String partName;
    private BigInteger quantity;
    private String unitOfMeasure;

    public OperationViewModel() {
    }

    public String getArticleCode() {
        return this.articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    public String getPartName() {
        return this.partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public BigInteger getQuantity() {
        return this.quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    public String getUnitOfMeasure() {
        return this.unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }
}
