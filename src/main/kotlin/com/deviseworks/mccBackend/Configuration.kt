package com.deviseworks.mccBackend

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component


@Component
@ConfigurationProperties(prefix = "application")
class Configuration{
    lateinit var foo: String
}