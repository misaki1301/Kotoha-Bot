package com.shibuyaxpress.kotohabot.controllers

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @GetMapping("/", produces = [MediaType.TEXT_HTML_VALUE])
    fun theAnswer(): String = "<h1>Hello World</h1>"
}