package com.tsl.app.presenter;


import com.tsl.app.service.ServiceGenerator;
import com.tsl.app.service.UserService;
import com.tsl.app.service.requestdto.UserProfileRequest;
import com.tsl.app.service.responsedto.GeneralResponse;
import com.tsl.app.view.BaseView;

import retrofit2.Callback;

public class SignupPresenter {

    private String TAG = this.getClass().getName();

    private UserService userService;

    private BaseView view;

    public SignupPresenter(BaseView view) {
        this.view = view;
        // No authentication header needed for signup, confirm request
        userService = ServiceGenerator.getProxyService(UserService.class, false);
    }

    public void signup(UserProfileRequest data, Callback<GeneralResponse> callback) {
        userService.saveUser(data).enqueue(callback);
    }




}
