package com.rabbit.wechatBot.http;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRetrofit {

    public UserApi userApi;
    public static final String USER_BASE_URL = "https://qyapi.weixin.qq.com/";

    public UserApi getUserApi() {
        return userApi;
    }

    ApiRetrofit() {
//        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//            public void log(String s) {
//                //打印日志
//            }
//        });
//        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(logInterceptor)
                .build();
        Retrofit retrofit_user = new Retrofit.Builder()
                .baseUrl(USER_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userApi = retrofit_user.create(UserApi.class);

    }
}