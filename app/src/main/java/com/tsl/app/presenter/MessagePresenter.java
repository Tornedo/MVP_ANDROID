package com.tsl.app.presenter;



import com.tsl.app.service.ServiceGenerator;
import com.tsl.app.service.MessageService;
import com.tsl.app.service.responsedto.GeneralResponse;
import com.tsl.app.service.responsedto.tsl.MessageListResponse;
import com.tsl.app.service.responsedto.tsl.MessageSendRequest;
import com.tsl.app.util.Constants;
import com.tsl.app.view.BaseView;

import java.util.ArrayList;

import retrofit2.Callback;

public class MessagePresenter {

    private String TAG = this.getClass().getName();

    private MessageService service;

    private BaseView view;

    public MessagePresenter(BaseView view) {
        this.view = view;
        ServiceGenerator.setHeaderInterceptor(Constants.AUTH_TOKEN, "");
        service = ServiceGenerator.getService(MessageService.class);
    }

    /////
    public void sendMessage(MessageSendRequest request, Callback<GeneralResponse> callback) {
        service.sendMessage(request).enqueue(callback);
    }

    public void getMessage(Callback<ArrayList<MessageListResponse>> callback) {
        service.getMessage().enqueue(callback);
    }


}
