package com.turo.coroutinedemo

import com.turo.coroutinedemo.external.MockExternalDataSource
import com.turo.coroutinedemo.external.MockExternalDataSource2
import com.turo.coroutinedemo.thirdpartylibraries.MockThirdPartyLibrary
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Instant
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

private const val EXECUTOR_THREAD_COUNT = 10

@Service
@Profile("!number")
class SimpleWordService @Autowired
constructor(
    private val config: SimpleWordConfig,
    private val externalDataSource: MockExternalDataSource,
    private val externalDataSource2: MockExternalDataSource2
) : SimpleService {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val executorService = Executors.newFixedThreadPool(EXECUTOR_THREAD_COUNT)

    override fun get(): SimpleDto {

        logger.info("GET - start: ${Thread.currentThread()}")

        val externalData1Task = CompletableFuture.supplyAsync({
            val data = externalDataSource.getExternalData(config.variable!!, config.secret!!)
            logger.info("GET - external data 1: ${Thread.currentThread()}")
            return@supplyAsync data
        }, executorService)

        val longRunningTask = CompletableFuture.supplyAsync({
            val result = MockThirdPartyLibrary.longRunningCalculation()
            logger.info("GET - long running calculation: ${Thread.currentThread()}")
            return@supplyAsync result
        }, executorService)

        logger.info("GET - post data fetch: ${Thread.currentThread()}")

        val externalData2 = externalDataSource2.getExternalData(
            externalData1Task.get(),
            longRunningTask.get()
        )

        return SimpleDto(
            externalData1Task.get(),
            externalData2,
            longRunningTask.get(),
            Instant.now()
        )
    }

    override fun getMono(): Mono<SimpleDto> {

        logger.info("GET MONO - start: ${Thread.currentThread()}")

        val longRunningTaskMono: Mono<String> = Mono.defer { Mono.just(MockThirdPartyLibrary.longRunningCalculation()) }
        val externalDataMono = externalDataSource.getExternalDataMono(config.variable!!, config.secret!!)

        val simpleDtoMono = Mono.zip(externalDataMono, longRunningTaskMono)
            .flatMap {
                logger.info("GET MONO - pre-externalData2: ${Thread.currentThread()}")
                externalDataSource2.getExternalDataMono(it.t1, it.t2)
            }
            .map {
                logger.info("GET MONO - map response: ${Thread.currentThread()}")

                SimpleDto(
                    config.variable!!,
                    config.secret!!,
                    it,
                    Instant.now()
                )
            }

        logger.info("GET MONO - returned mono: ${Thread.currentThread()}")

        return simpleDtoMono
    }

    override suspend fun getSuspend(): SimpleDto = coroutineScope {

        logger.info("GET SUSPEND - start: ${Thread.currentThread()}")

        // NOTE that there is an issue with this code intentionally
        val externalDataDeferred = async {
            logger.info("GET SUSPEND - pre external data 1 fetch: ${Thread.currentThread()}")
            val data = externalDataSource.getExternalDataSuspend(config.variable!!, config.secret!!)
            logger.info("GET SUSPEND - post external data 1 fetch: ${Thread.currentThread()}")
            return@async data
        }
        val longRunningTaskDeferred = async {
            logger.info("GET SUSPEND - pre long running task: ${Thread.currentThread()}")
            val data = MockThirdPartyLibrary.longRunningCalculation()
            logger.info("GET SUSPEND - post long running task: ${Thread.currentThread()}")
            return@async data
        }

        logger.info("GET SUSPEND - post data fetch: ${Thread.currentThread()}")

        val externalData2 = externalDataSource2.getExternalDataSuspend(
            externalDataDeferred.await(),
            longRunningTaskDeferred.await()
        )

        return@coroutineScope SimpleDto(
            externalDataDeferred.await(),
            externalData2,
            longRunningTaskDeferred.await(),
            Instant.now()
        )
    }
}
