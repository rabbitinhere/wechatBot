package com.rabbit.wechatBot.http;

import com.rabbit.wechatBot.bean.BotRequest;
import com.rabbit.wechatBot.bean.BotResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserApi {

    @Headers("Content-Type: application/json")
    @POST("cgi-bin/webhook/send?key=e113b9de-9d0f-4147-8f14-ea244c23063a")
    Call<BotResponse> listRepos(@Body BotRequest useregister);
}