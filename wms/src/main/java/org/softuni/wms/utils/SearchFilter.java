package org.softuni.wms.utils;

public class SearchFilter {

    private String value;
    private String type;
    private String direction;
    private String orderBy;

    public SearchFilter() {
    }

    public SearchFilter(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public SearchFilter(String value, String type, String direction, String orderBy) {
        this.value = value;
        this.type = type;
        this.direction = direction;
        this.orderBy = orderBy;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getOrderBy() {
        return this.orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
