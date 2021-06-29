package com.tsl.app.service;

import java.util.Date;
import java.util.List;

public class Error {

    private int status;
    private String title;
    private String detail;
    private Date timeStamp;
    private String developerMessage;
    private List<InputError> errors;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public List<InputError> getErrors() {
        return errors;
    }

    public void setErrors(List<InputError> errors) {
        this.errors = errors;
    }

    public boolean hasValidationError() {

        return (getErrors() != null && getErrors().size() > 0);
    }

    public String getValidationError() {
        String err = "";

        if (hasValidationError()) {
            InputError ie = getErrors().get(0);
            err = ie.toString();
        }

        return err;
    }
}

