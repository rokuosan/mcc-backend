package com.deviseworks.mccBackend.controller

import com.deviseworks.mccBackend.Configuration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController(
    val prop: Configuration
) {

    @GetMapping("/")
    fun displayValues(): String{
        return prop.foo
    }
}