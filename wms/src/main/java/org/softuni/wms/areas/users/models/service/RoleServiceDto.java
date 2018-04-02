package org.softuni.wms.areas.users.models.service;

public class RoleServiceDto {

    private Long id;
    private String authority;

    public RoleServiceDto() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
