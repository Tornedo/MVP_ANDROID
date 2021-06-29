package com.tsl.app;

import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDexApplication;


public class UfrilApp extends MultiDexApplication {
    private static Context appContext;


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
 
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

   
    public static Context getAppContext() {
        return appContext;
    }


}