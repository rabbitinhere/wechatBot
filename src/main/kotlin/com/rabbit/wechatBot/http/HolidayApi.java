package com.rabbit.wechatBot.http;

import com.rabbit.wechatBot.bean.HolidayStatusResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface HolidayApi {

    @GET("holiday")
    Call<List<HolidayStatusResponse>> requestStatus(@Query("date") String date);
}