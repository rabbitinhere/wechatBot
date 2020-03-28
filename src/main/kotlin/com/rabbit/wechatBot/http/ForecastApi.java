package com.rabbit.wechatBot.http;

import com.rabbit.wechatBot.bean.ForecastResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ForecastApi {

    @GET("113.9164,22.4976/forecast.json")
    Call<ForecastResponse> requestForecast();
}