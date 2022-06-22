package com.turo.coroutinedemo

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@ConfigurationProperties("simple-number")
@Profile("number")
class SimpleNumberConfig(val variable: Double, val secret: Double)
