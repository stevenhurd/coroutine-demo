package com.turo.coroutinedemo.external

import kotlinx.coroutines.delay
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.Duration

private const val SLEEP_IN_MS = 5000L

@Component
class MockExternalDataSource2 {

    // simulates an external call taking some data and returning something
    fun getExternalData(dataSource1Result: String, longRunningCalculationResult: String): String {

        Thread.sleep(SLEEP_IN_MS)
        return "$dataSource1Result:$longRunningCalculationResult:sync"
    }

    // simulates an external call through a reactive-aware client
    fun getExternalDataMono(dataSource1Result: String, longRunningCalculationResult: String): Mono<String> {

        return Mono.delay(Duration.ofMillis(SLEEP_IN_MS))
            .map { "$dataSource1Result:$longRunningCalculationResult:mono" }
            .subscribeOn(Schedulers.boundedElastic())
    }

    // simulates an external call through a coroutine enabled client
    suspend fun getExternalDataSuspend(dataSource1Result: String, longRunningCalculationResult: String): String {

        delay(SLEEP_IN_MS)
        return "$dataSource1Result:$longRunningCalculationResult:suspend"
    }
}
