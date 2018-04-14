package org.softuni.wms.areas.parts.criteria;

public class PartSearchCriteria {
    private String key;
    private String operation;
    private Object value;


    public PartSearchCriteria(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value =value;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOperation() {
        return this.operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
