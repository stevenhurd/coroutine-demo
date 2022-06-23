package com.turo.coroutinedemo

import com.turo.coroutinedemo.external.MockExternalDataSource
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Instant
import java.util.concurrent.CompletableFuture

@Service
@Profile("!number")
class SimpleWordService @Autowired
constructor(
    private val config: SimpleWordConfig,
    private val externalDataSource: MockExternalDataSource
) : SimpleService {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun get(): SimpleDto {

        logger.info("GET - start: ${Thread.currentThread()}")

        val externalData = externalDataSource.getExternalData(config.variable!!, config.secret!!)

        logger.info("GET - post data fetch: ${Thread.currentThread()}")

        return SimpleDto(
            config.variable!!,
            config.secret!!,
            externalData,
            Instant.now()
        )
    }

    @Async
    override fun getAsync(delaySeconds: Long): CompletableFuture<SimpleDto> {

        logger.info("GET ASYNC - start: ${Thread.currentThread()}")
        return CompletableFuture.supplyAsync {
            try {
                logger.info("GET ASYNC - supplier start: ${Thread.currentThread()}")

                val externalData = externalDataSource.getExternalData(config.variable!!, config.secret!!)

                logger.info("GET ASYNC - post data fetch: ${Thread.currentThread()}")

                return@supplyAsync SimpleDto(
                    config.variable!!,
                    config.secret!!,
                    externalData,
                    Instant.now()
                )
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }

            get()
        }
    }

    override fun getMono(): Mono<SimpleDto> {

        logger.info("GET MONO - start: ${Thread.currentThread()}")

        val externalDataMono = externalDataSource.getExternalDataMono(config.variable!!, config.secret!!)
            .map {

                logger.info("GET MONO - post data fetch: ${Thread.currentThread()}")

                SimpleDto(
                    config.variable!!,
                    config.secret!!,
                    it,
                    Instant.now()
                )
            }

        logger.info("GET MONO - returned mono: ${Thread.currentThread()}")

        return externalDataMono
    }

    override suspend fun getSuspend(): SimpleDto {

        logger.info("GET SUSPEND - start: ${Thread.currentThread()}")

        val externalData = externalDataSource.getExternalDataSuspend(config.variable!!, config.secret!!)

        logger.info("GET SUSPEND - post data fetch: ${Thread.currentThread()}")

        return SimpleDto(
            config.variable!!,
            config.secret!!,
            externalData,
            Instant.now()
        )
    }
}
