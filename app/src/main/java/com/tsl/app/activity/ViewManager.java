package com.tsl.app.activity;


import android.content.Context;
import android.content.Intent;
import android.support.v4.content.IntentCompat;

import com.tsl.app.UfrilApp;


public class ViewManager {

    public static Intent getLoginActivity(Context ctx) {

        Intent intent = new Intent(ctx != null ? ctx : UfrilApp.getAppContext(), LoginActivity.class);
        intent = IntentCompat.makeRestartActivityTask(intent.getComponent());
        return intent;
    }

    public static Intent getSignupActivity(Context ctx) {
        return new Intent(ctx != null ? ctx :  UfrilApp.getAppContext(), SignupActivity.class);
    }



    public static Intent getMainActivity(Context ctx) {
        Intent intent = new Intent(ctx != null ? ctx :  UfrilApp.getAppContext(), MainActivity.class);
        intent = IntentCompat.makeRestartActivityTask(intent.getComponent());
        return intent;
    }



    public static Intent getActiveItemsActivity(Context ctx) {
        return new Intent(ctx != null ? ctx :  UfrilApp.getAppContext(), MessageListActivity.class);
    }










}

