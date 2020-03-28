package com.rabbit.wechatBot.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiRetrofit internal constructor() {
    var wechatApi: WechatApi
    var forecastApi: ForecastApi

    companion object {
        const val WECHAT_URL = "https://qyapi.weixin.qq.com/"

        /**
         * 对接了彩云天气API：http://wiki.swarma.net/index.php/%E5%BD%A9%E4%BA%91%E5%A4%A9%E6%B0%94API/v2
         */
        const val FORECAST_URL = "https://api.caiyunapp.com/v2/TAkhjf8d1nlSlspN/"
    }

    init {
        val client = OkHttpClient.Builder()
                .build()
        val retrofitUser = Retrofit.Builder()
                .baseUrl(WECHAT_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        wechatApi = retrofitUser.create(WechatApi::class.java)
        val retrofitForecast = Retrofit.Builder()
                .baseUrl(FORECAST_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        forecastApi = retrofitForecast.create(ForecastApi::class.java)
    }
}