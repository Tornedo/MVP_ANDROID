package com.tsl.app.presenter;


import com.tsl.app.service.ServiceGenerator;
import com.tsl.app.service.UserService;
import com.tsl.app.service.responsedto.tsl.GetTokenResponse;

import retrofit2.Callback;

public class LoginPresenter {

    private UserService userService;

    public LoginPresenter() {
        userService = ServiceGenerator.getProxyService(UserService.class, true);
    }

    public void getUserToken(String username, String password, Callback<GetTokenResponse> callback) {
        // Set basic authorization header interceptor
        ServiceGenerator.setHeaderInterceptor(username, password);
        userService = ServiceGenerator.getProxyService(UserService.class, false);
        userService.getToken().enqueue(callback);
    }
}
