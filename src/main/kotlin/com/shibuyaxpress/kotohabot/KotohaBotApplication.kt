package com.shibuyaxpress.kotohabot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotohaBotApplication

fun main(args: Array<String>) {
    runApplication<KotohaBotApplication>(*args)
}
