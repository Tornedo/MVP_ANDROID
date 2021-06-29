package com.tsl.app.view;

public interface Processing {

    void showProcessing();

    /**
     * Show processing dialog
     *
     * @param message
     * @param title
     */
    void showProcessing(String message, String title);

    /**
     * Hide processing dialog
     */
    void hideProcessing();

}
