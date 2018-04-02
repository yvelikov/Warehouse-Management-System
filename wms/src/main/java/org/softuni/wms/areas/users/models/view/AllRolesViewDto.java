package org.softuni.wms.areas.users.models.view;

import java.util.ArrayList;
import java.util.List;

public class AllRolesViewDto {
    private List<String> roles;

    public AllRolesViewDto() {
        this.roles = new ArrayList<>();
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void add(String role){
        this.roles.add(role);
    }
}
