package com.turo.coroutinedemo

import java.time.Instant

data class SimpleDto(
    var externalData1: String? = null,
    var externalData2: String? = null,
    var longRunningCalculation: String? = null,
    var timestamp: Instant? = null
)
