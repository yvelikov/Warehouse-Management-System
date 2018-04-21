package org.softuni.wms.areas.users.models.binding;

import org.softuni.wms.constants.Constants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RegisterUserDto {

    @NotEmpty(message = Constants.USERNAME_NOT_EMPTY)
    @Size(min = 3, max = 20, message = Constants.USERNAME_LENGTH)
    private String username;

    @NotEmpty(message = Constants.FIST_NAME_NOT_EMPTY)
    private String firstName;

    @NotEmpty(message = Constants.LAST_NAME_NOT_EMPTY)
    private String lastName;

    @NotEmpty(message = Constants.EMAIL_NOT_EMPTY)
    private String email;

    @NotEmpty(message = Constants.PASSWORD_NOT_EMPTY)
    @Size(min = 4, max = 32, message = Constants.PASSWORD_LENGTH)
    private String password;

    @NotEmpty(message = Constants.PASSWORD_NOT_EMPTY)
    @Size(min = 4, max = 32, message = Constants.PASSWORD_LENGTH)
    private String confirmPassword;

    public RegisterUserDto() {
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
