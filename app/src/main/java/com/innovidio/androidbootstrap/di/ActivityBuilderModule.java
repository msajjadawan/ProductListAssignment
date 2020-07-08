package com.innovidio.androidbootstrap.di;

import com.innovidio.androidbootstrap.activity.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();



}
