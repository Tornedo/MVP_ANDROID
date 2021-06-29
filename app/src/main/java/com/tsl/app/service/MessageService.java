package com.tsl.app.service;



import com.tsl.app.service.responsedto.GeneralResponse;
import com.tsl.app.service.responsedto.tsl.MessageListResponse;
import com.tsl.app.service.responsedto.tsl.MessageSendRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MessageService {

    @POST("messages")
    Call<GeneralResponse> sendMessage(@Body MessageSendRequest request);

    @GET("messages")
    Call<ArrayList<MessageListResponse>> getMessage();
}
