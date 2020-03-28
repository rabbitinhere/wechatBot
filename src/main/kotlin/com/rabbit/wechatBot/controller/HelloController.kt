package com.rabbit.wechatBot.controller

import com.rabbit.wechatBot.http.ApiFactory
import com.rabbit.wechatBot.bean.BotRequest
import com.rabbit.wechatBot.bean.BotResponse
import com.rabbit.wechatBot.bean.TextBean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import retrofit2.Call
import retrofit2.Response


@RestController
class HelloController {
    @GetMapping("/hello")
    fun hello(): String{
        val textBean = TextBean("from spring boot community idea")
        val para = BotRequest("text", textBean)
        val call: Call<BotResponse> = ApiFactory.getUserApi().listRepos(para)
        val response: Response<BotResponse> = call.execute()
        return if (!response.isSuccessful) {
            throw Exception(response.message())
        } else {
            response.body().toString()
        }
    }

}