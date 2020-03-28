package com.rabbit.wechatBot.timer

import com.rabbit.wechatBot.http.ApiFactory
import com.rabbit.wechatBot.bean.BotRequest
import com.rabbit.wechatBot.bean.BotResponse
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
     * 每天晚上的 秒 分 时   运行
     */
    @Scheduled(cron = "59 56 18 * * ?")
    fun initTimerOne() {
        val textBean = TextBean("from spring boot community idea")
        val para = BotRequest("text", textBean)
        val call: Call<BotResponse> = ApiFactory.getUserApi().listRepos(para)
        val response: Response<BotResponse> = call.execute()
        println("打印时间1")
    }
}