package com.rabbit.wechatBot.timer

import com.rabbit.wechatBot.bean.*
import com.rabbit.wechatBot.http.ApiFactory
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

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
            text = "天气预报：${forecastResponse.body().result.hourly.description}" +
                    " 2小时后降雨量：" +forecastResponse.body().result.hourly.precipitation[2].value + "mm" +
                    " 2小时后温度：" +forecastResponse.body().result.hourly.temperature[2].value + "摄氏度"
            sendText2Wechat(text)
        }
    }

    @Scheduled(cron = "00 20 09 ? * MON-FRI")
    fun checkIn() {
        val isNotHoliday = isNotHoliday()
        if(isNotHoliday == null){
            sendText2Wechat("拿不到节假日数据，谁来帮帮我~~")
        }else if(isNotHoliday){
            sendText2Wechat("还没打卡？ 你还有10分钟时间咯~")
        }
    }

    @Scheduled(cron = "00 20 18 ? * MON-FRI")
    fun checkOut1() {
        val isNotHoliday = isNotHoliday()
        if(isNotHoliday == null){
            sendText2Wechat("拿不到节假日数据，谁来帮帮我~~")
        }else if(isNotHoliday){
            sendText2Wechat("下班了，记得打卡")
        }
    }

    @Scheduled(cron = "00 30 19 ? * MON-FRI")
    fun checkOut2() {
        val isNotHoliday = isNotHoliday()
        if(isNotHoliday == null){
            sendText2Wechat("拿不到节假日数据，谁来帮帮我~~")
        }else if(isNotHoliday){
            sendText2Wechat("还没走？，一会儿别忘记打卡")
        }
    }

    private fun sendText2Wechat(text: String){
        val textBean = TextBean(text)
        val para = BotRequest("text", textBean)
        val wechatCall: Call<BotResponse> = ApiFactory.apiRetrofit.wechatApi.sendText(para)
        wechatCall.execute()
    }

    private fun isNotHoliday(): Boolean?{
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val curDate = formatter.format(Date())
        val call: Call<List<HolidayStatusResponse>> = ApiFactory.apiRetrofit.holidayApi.requestStatus(curDate)
        val response: Response<List<HolidayStatusResponse>> = call.execute()
        return if (!response.isSuccessful) {
            null
        } else {
            response.body()[0].status == 0 || response.body()[0].status == 2
        }
    }
}