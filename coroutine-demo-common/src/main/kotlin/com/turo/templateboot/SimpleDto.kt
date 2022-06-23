package com.turo.coroutinedemo

import java.time.Instant

data class SimpleDto(
    var variable: String? = null,
    var secret: String? = null,
    var extraData: String? = null,
    var timestamp: Instant? = null
)
