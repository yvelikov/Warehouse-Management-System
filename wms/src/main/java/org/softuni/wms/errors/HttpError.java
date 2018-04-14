package org.softuni.wms.errors;

public class HttpError {

    private String status;
    private String description;
    private String message;

    public HttpError() {
    }

    public HttpError(String status, String description, String message) {
        this.status = status;
        this.description = description;
        this.message = message;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
