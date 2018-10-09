package com.example.library.http;

import com.example.library.api.API;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    public static final String BASE_URL = "http://122.152.231.185/";
    private Retrofit retrofit;
    private static RetrofitUtil sInstance;
    public RetrofitUtil(String url) {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static RetrofitUtil getInstance(String url) {
        synchronized (RetrofitUtil.class) {
            if (sInstance == null) {
                sInstance = new RetrofitUtil(url);
            }
        }
        return sInstance;
    }

    public API getServerices() {
        return retrofit.create(API.class);
    }
}