package com.turo.coroutinedemo

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@ConfigurationProperties("simple")
@Profile("!number")
class SimpleWordConfig {
    var variable: String? = null
    var secret: String? = null
}
