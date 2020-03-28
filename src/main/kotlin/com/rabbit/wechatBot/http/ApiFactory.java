package com.rabbit.wechatBot.http;

public class ApiFactory {
    protected static final Object monitor = new Object();
    static UserApi userApi = null;

    public static UserApi getUserApi() {
        synchronized (monitor) {
            if (userApi == null) {
                userApi = new ApiRetrofit().getUserApi();
            }
            return userApi;
        }
    }
}