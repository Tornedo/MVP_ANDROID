package com.tsl.app.model;



public abstract class ListViewItem {
    protected transient String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
