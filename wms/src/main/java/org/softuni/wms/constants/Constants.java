package org.softuni.wms.constants;

public final class Constants {

    public static final String INVALID_CREDENTIALS = "Invalid username or password";
    public static final String LOGOUT_SUCCESSFUL = "Logged out successfully!";

    public static final String DELIVERY = "DELIVERY";
    public static final String ISSUE = "ISSUE";
    public static final String ACTION_RESULT_MESSAGE = "User \'%s\' successfully %s";
    public static final String ACTION_RESULT_NOT_COMPLETED = "Your action could not be completed";

    public static final String APPLICATION_EMAIL = "wms.yordan@gmail.com";
    public static final String EMAIL_CONTENT = "You have been successfully registered to WMS." +
            " You can login with the following credentials:%n username: %s%n password: %s%n" +
            " For more information you can contact your administrator.";
    public static final String EMAIL_SUBJECT = "Your Account @WMS";

    public static final String PASSWORD_ERROR = "Passwords must match";
    public static final String USERNAME_ERROR = "Username already taken";
    public static final String EMAIL_ERROR = "E-mail already in use";

    public static final String USERNAME_NOT_EMPTY = "Username cannot be empty";
    public static final String USERNAME_LENGTH = "Username must be between 3 and 20 symbols long";
    public static final String FIST_NAME_NOT_EMPTY = "First name cannot be empty";
    public static final String LAST_NAME_NOT_EMPTY = "Last name cannot be empty";
    public static final String EMAIL_NOT_EMPTY = "Email cannot be empty";
    public static final String PASSWORD_NOT_EMPTY = "Password cannot be empty";
    public static final String PASSWORD_LENGTH = "Password must be between 4 and 32 symbols long";
    public static final String AT_LEAST_ONE_ROLE = "At least one role must be selected";

    public static final String SUCCESSFUL_DELIVERY = "Parts successfully delivered!";
    public static final String SUCCESSFUL_ISSUE = "Parts successfully issued!";
}
