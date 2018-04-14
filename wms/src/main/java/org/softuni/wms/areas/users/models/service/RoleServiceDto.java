package org.softuni.wms.areas.users.models.service;

public class RoleServiceDto {

    private String id;
    private String authority;

    public RoleServiceDto() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
