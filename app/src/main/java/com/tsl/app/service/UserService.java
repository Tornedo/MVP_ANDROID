package com.tsl.app.service;


import com.tsl.app.service.requestdto.UserProfileRequest;
import com.tsl.app.service.responsedto.GeneralResponse;
import com.tsl.app.service.responsedto.tsl.GetTokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    @POST("users")
    Call<GeneralResponse> saveUser(@Body UserProfileRequest user);


    @GET("token")
    Call<GetTokenResponse> getToken();
}
