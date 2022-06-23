package com.turo.coroutinedemo.external

import kotlinx.coroutines.delay
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Duration

private const val SLEEP_IN_MS = 1000L

@Component
class MockExternalDataSource {

    // simulates an external call taking some data and returning something
    fun getExternalData(variable: String, secret: String): String {

        Thread.sleep(SLEEP_IN_MS)
        return "$variable:$secret:sync"
    }

    // simulates an external call through a reactive-aware client
    fun getExternalDataMono(variable: String, secret: String): Mono<String> {

        return Mono.delay(Duration.ofMillis(SLEEP_IN_MS))
            .map { "$variable:$secret:mono" }
    }

    // simulates an external call through a coroutine enabled client
    suspend fun getExternalDataSuspend(variable: String, secret: String): String {

        delay(SLEEP_IN_MS)
        return "$variable:$secret:suspend"
    }
}
