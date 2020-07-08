package com.innovidio.androidbootstrap.di;


import android.util.Log;

import com.innovidio.androidbootstrap.Constants;
import com.innovidio.androidbootstrap.network.APIService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class NetworkModule {

    @Singleton
    @Provides
    static public APIService getApiService(Retrofit retrofit){
        return retrofit.create(APIService.class);
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance(){
       return new Retrofit.Builder()
                .baseUrl(Constants.URL_BASE)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }
}
