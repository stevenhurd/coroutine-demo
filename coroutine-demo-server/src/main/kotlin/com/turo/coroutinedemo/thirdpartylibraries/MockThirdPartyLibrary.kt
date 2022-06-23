package com.turo.coroutinedemo.thirdpartylibraries

import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

object MockThirdPartyLibrary {

    private const val SLEEP_IN_MS = 5000L
    private const val EXECUTOR_THREAD_COUNT = 10

    private val logger = LoggerFactory.getLogger(javaClass)
    private val executorService = Executors.newFixedThreadPool(EXECUTOR_THREAD_COUNT)

    // represents a piece of code that might take a long time, spin up an
    // arbitrary number of threads, and we have no access to the source
    // to update
    fun longRunningCalculation(): String {

        logger.info("LongRunningCalculation")

        return CompletableFuture.supplyAsync(
            {
                Thread.sleep(SLEEP_IN_MS)
                return@supplyAsync "longRunningCalculationOutput"
            },
            executorService
        ).get()
    }
}
