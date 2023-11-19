package com.example.testappdans.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRetrofit {
    private Retrofit retrofit;

    public ApiRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://dev6.dansmultipro.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public ApiDans getApiDans() {
        return retrofit.create(ApiDans.class);
    }
}
