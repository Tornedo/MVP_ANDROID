package com.tsl.app.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.tsl.app.R;
import com.tsl.app.databinding.FragmentHomeBinding;
import com.tsl.app.presenter.MessagePresenter;
import com.tsl.app.service.responsedto.GeneralResponse;
import com.tsl.app.service.responsedto.tsl.MessageSendRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends BaseFragment {
    private FragmentHomeBinding binding;
    private MessagePresenter taskerPresenter;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        final View view = binding.getRoot();
        super.setContext(getActivity());
        taskerPresenter = new MessagePresenter(null);
        binding.sendMessageClicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
        return view;
    }

    private void sendMessage() {
        if (binding.message.getText().toString().isEmpty()){
            showToast(getString(R.string.empty_message_error));
        }else{
            showProcessing();
            MessageSendRequest messageSendRequest = new MessageSendRequest();
            messageSendRequest.setMessage(binding.message.getText().toString());
            taskerPresenter.sendMessage(messageSendRequest, messageSendResponse);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void onError(String error) {
        hideProcessing();
        showToast(error);
    }

    public void onSuccess(String error) {
        hideProcessing();
        showToast(error);
    }

    private Callback<GeneralResponse> messageSendResponse = new Callback<GeneralResponse>() {
        @Override
        public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
            Log.e("response code ", response.code() + "");
            if (response.isSuccess()) {
               onSuccess(getString(R.string.message_successPmessage));
            } else {
                onError(getString(R.string.generic_error));
            }
        }

        @Override
        public void onFailure(Call<GeneralResponse> call, Throwable t) {
            onError(t.getMessage());
            Log.e(TAG, t.getMessage(), t);
        }
    };

}
