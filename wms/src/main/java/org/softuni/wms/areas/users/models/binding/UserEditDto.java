package org.softuni.wms.areas.users.models.binding;

import org.softuni.wms.constants.Constants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

public class UserEditDto {

    private String id;

    @NotEmpty(message = Constants.USERNAME_NOT_EMPTY)
    @Size(min = 3, max = 20, message = Constants.USERNAME_LENGTH)
    private String username;

    @NotEmpty(message = Constants.FIST_NAME_NOT_EMPTY)
    private String firstName;

    @NotEmpty(message = Constants.LAST_NAME_NOT_EMPTY)
    private String lastName;

    @NotEmpty(message = Constants.EMAIL_NOT_EMPTY)
    private String email;

    @NotEmpty(message = Constants.AT_LEAST_ONE_ROLE)
    private Set<String> authorities;

    public UserEditDto() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

}
