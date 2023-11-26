package com.shibuyaxpress.kotohabot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
class KotohaBotApplication

fun main(args: Array<String>) {
    runApplication<KotohaBotApplication>(*args)
}
