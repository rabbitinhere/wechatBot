package com.rabbit.wechatBot.timer

import com.rabbit.wechatBot.http.ApiFactory
import com.rabbit.wechatBot.bean.BotRequest
import com.rabbit.wechatBot.bean.BotResponse
import com.rabbit.wechatBot.bean.ForecastResponse
import com.rabbit.wechatBot.bean.TextBean
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import retrofit2.Call
import retrofit2.Response

@Component
@EnableScheduling
class Timer {
    /**
     * 天气预报定时器
     * 每天晚上的 秒 分 时   运行
     */
    @Scheduled(cron = "00 20 18 * * ?")
    fun forecastTimer() {
        val forecastCall: Call<ForecastResponse> = ApiFactory.apiRetrofit.forecastApi.requestForecast()
        val forecastResponse: Response<ForecastResponse> = forecastCall.execute()
        var text = ""
        if (!forecastResponse.isSuccessful) {
            text = "今天天气预报坏了，我没法提醒啦~"
        } else {
            text = "我收到了一份天气预报：${forecastResponse.body().result.hourly.description}"
        }

        val textBean = TextBean(text)
        val para = BotRequest("text", textBean)
        val wechatCall: Call<BotResponse> = ApiFactory.apiRetrofit.wechatApi.sendText(para)
        val wechatResponse: Response<BotResponse> = wechatCall.execute()
        if (!forecastResponse.isSuccessful) {
            //do nothing
        }
    }
}