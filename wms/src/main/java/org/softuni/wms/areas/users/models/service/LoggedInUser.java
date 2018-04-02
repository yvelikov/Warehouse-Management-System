package org.softuni.wms.areas.users.models.service;


import org.softuni.wms.areas.users.entities.Role;

import java.util.Set;

public class LoggedInUser {

    private String username;
    private String password;
    private Set<Role> authorities;

    public LoggedInUser() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

}
