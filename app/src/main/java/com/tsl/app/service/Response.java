package com.tsl.app.service;

public class Response {

    private String statuss;
    private Error error;


    public Response() {
        error = new Error();
    }

    public String getStatuss() {
        return statuss;
    }

    public void setStatuss(String statuss) {
        this.statuss = statuss;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

}
