package com.turo.templateboot

import java.time.Instant

data class SimpleDto(
    var variable: String? = null,
    var secret: String? = null,
    var timestamp: Instant? = null
)
