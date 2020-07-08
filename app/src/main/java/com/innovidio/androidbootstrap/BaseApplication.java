package com.innovidio.androidbootstrap;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.innovidio.androidbootstrap.di.AppComponent;
import com.innovidio.androidbootstrap.di.DaggerAppComponent;


import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class BaseApplication extends DaggerApplication {

    private static final String TAG = "BaseApplication";
//    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    public void onCreate() {

        super.onCreate();
        Stetho.initializeWithDefaults(this);
        // Obtain the FirebaseAnalytics instance.
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


    }


    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().applicattion(this).build();
        return appComponent;
    }
}
