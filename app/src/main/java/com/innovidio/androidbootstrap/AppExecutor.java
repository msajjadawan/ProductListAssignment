package com.innovidio.androidbootstrap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppExecutor {

    private static ExecutorService sInstance;


    public static ExecutorService getExecutorService() {
        if (sInstance == null) {
//            sInstance = Executors.newSingleThreadExecutor();
            sInstance = Executors.newFixedThreadPool(3);
        }
        return sInstance;
    }
//     AppExecutor.getExecutorService().execute(()->{

//    });



    //    public Future<String> getLatestAppointmentDate(){
//        ExecutorService executor =  AppExecutor.getExecutorService();
//        Future<String> future = executor.submit(() -> {
//            return "";
//        });
//
//        return future;
//    }

}