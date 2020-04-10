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
    @Scheduled(cron = "00 30 08 ? * MON-FRI")
    fun forecastTimer() {
        val forecastCall: Call<ForecastResponse> = ApiFactory.apiRetrofit.forecastApi.requestForecast()
        val forecastResponse: Response<ForecastResponse> = forecastCall.execute()
        var text = ""
        if (!forecastResponse.isSuccessful) {
            text = "今天天气预报坏了，我没法提醒啦~"
        } else {
            text = "天气预报：现在：${forecastResponse.body().result.hourly.description}"
        }

        sendText2Wechat(text)
    }

    @Scheduled(cron = "00 20 09 ? * MON-FRI")
    fun checkIn() {
        sendText2Wechat("还没打卡？ 你还有10分钟时间咯~")
    }

    @Scheduled(cron = "00 20 18 ? * MON-FRI")
    fun checkOut1() {
        sendText2Wechat("下班了，记得打卡")
    }

    @Scheduled(cron = "00 30 19 ? * MON-FRI")
    fun checkOut2() {
        sendText2Wechat("还没走？，一会儿别忘记打卡")
    }

    private fun sendText2Wechat(text: String){
        val textBean = TextBean(text)
        val para = BotRequest("text", textBean)
        val wechatCall: Call<BotResponse> = ApiFactory.apiRetrofit.wechatApi.sendText(para)
        wechatCall.execute()
    }
}