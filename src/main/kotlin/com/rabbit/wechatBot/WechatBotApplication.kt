package com.rabbit.wechatBot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WechatBotApplication

fun main(args: Array<String>) {
	runApplication<WechatBotApplication>(*args)
}
