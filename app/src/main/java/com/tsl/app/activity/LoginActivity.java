package com.tsl.app.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.tsl.app.R;
import com.tsl.app.databinding.ActivityLoginBinding;
import com.tsl.app.presenter.LoginPresenter;
import com.tsl.app.service.responsedto.tsl.GetTokenResponse;
import com.tsl.app.view.BaseView;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        BaseView, TextView.OnEditorActionListener {
    private ActivityLoginBinding binding;
    private LoginPresenter presenter;
    private String username;
    private String password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // My base initiator
        super.setContext(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        presenter = new LoginPresenter();
        binding.loginPassword.setOnEditorActionListener(this);
        keypadOffonStart();
    }


    @Override
    public void onDestroy() {
        System.gc();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;
        if (v.getId() == binding.loginPassword.getId() &&
                actionId == EditorInfo.IME_ACTION_SEND) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            onClickLogin(v);
            handled = true;
        }
        return handled;
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        // TODO(Developer): Check error code and notify the user of error state and resolution.
    }


    public void onClickLogin(View view) {
        try {
            if (validateAndAuth()) {
                processLogin(username, password);
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
            hideProcessing();
            showToast(ex.getMessage());
        }
    }

    public void onClickSignup(View view) {
        startActivity(ViewManager.getSignupActivity(this));
    }



    private boolean validateAndAuth() {
        boolean valid = true;

        username = binding.loginUsername.getText().toString().trim();
        password = binding.loginPassword.getText().toString().trim();

        // validate username
        if (valid && username.isEmpty()) {
            binding.loginUsername.setError(getString(R.string.user_name_empty));
            valid = false;
        }
        // validate password
        if (valid && password.isEmpty()) {
            binding.loginPassword.setError(getString(R.string.password_empty));
            valid = false;
        }

        return valid;
    }

    private void processLogin(String username, String password) {
        try {
            showProcessing();
            presenter.getUserToken(username, password, userProfileResponseCallback);
        } catch (Exception ex) {
            hideProcessing();
            showToast(ex.getMessage());
            Log.e(TAG, ex.getMessage(), ex);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(Object data) {
        hideProcessing();
        showToast(data.toString());
        startActivity(ViewManager.getMainActivity(this));
    }

    @Override
    public void onError(String error) {
        hideProcessing();
        showToast(error);
    }

    private Callback<GetTokenResponse> userProfileResponseCallback = new Callback<GetTokenResponse>() {
        @Override
        public void onResponse(Call<GetTokenResponse> call, Response<GetTokenResponse> response) {

            Log.e("response code ", response.code() + "");
            if (response.isSuccess()) {
                onSuccess(getString(R.string.success));
            } else {
                onError(getString(R.string.login_error));
            }
        }

        @Override
        public void onFailure(Call<GetTokenResponse> call, Throwable t) {
            onError(t.getMessage());
            Log.e(TAG, t.getMessage(), t);
        }
    };


}
