package com.rabbit.wechatBot.controller

import com.rabbit.wechatBot.bean.*
import com.rabbit.wechatBot.http.ApiFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


@RestController
class HelloController {

    @GetMapping("/testController")
    fun testController(): String{
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val curDate = formatter.format(Date())
        val call: Call<List<HolidayStatusResponse>> = ApiFactory.apiRetrofit.holidayApi.requestStatus(curDate)
        val response: Response<List<HolidayStatusResponse>> = call.execute()
        return if (!response.isSuccessful) {
            throw Exception(response.message())
        } else {
            response.body().get(0).status.toString()
        }
    }

    @GetMapping("/hello")
    fun hello(): String{
        val textBean = TextBean("from spring boot community idea")
        val para = BotRequest("text", textBean)
        val call: Call<BotResponse> = ApiFactory.apiRetrofit.wechatApi.sendText(para)
        val response: Response<BotResponse> = call.execute()
        return if (!response.isSuccessful) {
            throw Exception(response.message())
        } else {
            response.body().toString()
        }
    }

    @GetMapping("/forecast")
    fun forecast(): String{
        val call: Call<ForecastResponse> = ApiFactory.apiRetrofit.forecastApi.requestForecast()
        val response: Response<ForecastResponse> = call.execute()
        return if (!response.isSuccessful) {
            throw Exception(response.message())
        } else {
            response.body().result.hourly.description +
                    " 2小时后降雨量：" +response.body().result.hourly.precipitation[2].value + "mm" +
                    " 2小时后温度：" +response.body().result.hourly.temperature[2].value + "摄氏度"
        }
    }

}