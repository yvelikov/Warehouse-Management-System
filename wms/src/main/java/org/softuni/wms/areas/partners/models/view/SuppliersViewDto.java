package org.softuni.wms.areas.partners.models.view;

import java.util.List;

public class SuppliersViewDto {

    private List<String> suppliers;

    public SuppliersViewDto() {
    }

    public List<String> getSuppliers() {
        return this.suppliers;
    }

    public void setSuppliers(List<String> suppliers) {
        this.suppliers = suppliers;
    }
}
