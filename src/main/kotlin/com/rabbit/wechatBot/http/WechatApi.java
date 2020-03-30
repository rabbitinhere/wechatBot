package com.rabbit.wechatBot.http;

import com.rabbit.wechatBot.bean.BotRequest;
import com.rabbit.wechatBot.bean.BotResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface WechatApi {

    @Headers("Content-Type: application/json")
    @POST("cgi-bin/webhook/send?key=")//注意填入webhook的key
    Call<BotResponse> sendText(@Body BotRequest useregister);
}