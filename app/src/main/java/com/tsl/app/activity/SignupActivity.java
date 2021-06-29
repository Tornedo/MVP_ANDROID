package com.tsl.app.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.tsl.app.AlertCallback;
import com.tsl.app.R;
import com.tsl.app.databinding.ActivitySignupBinding;
import com.tsl.app.presenter.SignupPresenter;
import com.tsl.app.service.requestdto.UserProfileRequest;
import com.tsl.app.service.responsedto.GeneralResponse;
import com.tsl.app.util.GsonUtil;
import com.tsl.app.view.BaseView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends BaseActivity implements BaseView {

    private ActivitySignupBinding binding;
    private SignupPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContext(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        presenter = new SignupPresenter(this);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void onClickSignup(View view) {
        try {
            validateAndSave();
        } catch (Exception ex) {
            hideProcessing();
            Log.e(TAG, ex.getMessage(), ex);
        }
    }

    public void onClickSignIn(View view) {
        onBackPressed();
    }

    private boolean validateAndSave() {
        boolean valid = true;
        UserProfileRequest request = new UserProfileRequest();
        request.setUsername(binding.username.getText().toString().trim());
        request.setPassword(binding.signupPassword.getText().toString());

        if (valid && request.getUsername().isEmpty()) {
            binding.username.setError("Invalid username ");
            valid = false;
        }

        if (valid && request.getPassword().isEmpty()) {
            binding.signupPassword.setError("Field is empty");
            valid = false;
        }

        if (valid) {
            showProcessing();
            Log.e("request", GsonUtil.toJson(request));
            presenter.signup(request, signupResponseCallback);
        }
        Log.e("request", GsonUtil.toJson(request));
        return valid;
    }

    @Override
    public void onSuccess(Object data) {
        hideProcessing();
        showAlert("", data.toString(), new AlertCallback() {
            @Override
            public void run() {
                finish();
            }
        });
    }

    @Override
    public void onError(String message) {
        hideProcessing();
        showToast(message);
    }

    private Callback<GeneralResponse> signupResponseCallback = new Callback<GeneralResponse>() {
        @Override
        public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
            GeneralResponse data = response.body();
            if (response.isSuccess()) {
                onSuccess("User Created, Your user name is " + data.getUsername());
            } else {
                onError(data.getStatus());
            }
        }

        @Override
        public void onFailure(Call<GeneralResponse> call, Throwable t) {
            onError(t.getMessage());
            Log.e(TAG, t.getMessage(), t);
        }
    };

}
