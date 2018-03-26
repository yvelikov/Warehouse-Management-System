package org.softuni.wms.models.service;

import javax.validation.constraints.NotEmpty;

public class UserLoginDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    public UserLoginDto() {
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
}
