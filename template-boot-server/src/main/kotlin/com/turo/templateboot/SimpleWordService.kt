package com.turo.templateboot

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.concurrent.CompletableFuture

private const val TO_MILLIS = 1000
@Service
@Profile("!number")
class SimpleWordService @Autowired
constructor(private val config: SimpleWordConfig) : SimpleService {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun get(): SimpleDto {
        val simpleDto = SimpleDto(config.variable!!, config.secret!!, Instant.now())
        logger.info("complete request: {}", simpleDto)
        return simpleDto
    }

    @Async
    override fun getAsync(delaySeconds: Long): CompletableFuture<SimpleDto> {
        return CompletableFuture.supplyAsync {
            logger.info("starting request: {}s delay", delaySeconds)
            try {
                Thread.sleep(delaySeconds * TO_MILLIS)
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }

            get()
        }
    }
}
