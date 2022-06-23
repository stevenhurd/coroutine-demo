package com.turo.coroutinedemo

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class SimpleController(private val service: SimpleService) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/sample")
    fun doGet(): SimpleDto {

        logger.info("GET controller - request entry: ${Thread.currentThread()}")

        val simpleDto = service.get()

        logger.info("GET controller - response: ${Thread.currentThread()}")

        return simpleDto
    }

    @GetMapping("/sample-reactive")
    fun doGetReactive(): Mono<SimpleDto> {

        logger.info("GET MONO controller - request entry: ${Thread.currentThread()}")

        val simpleDtoMono = service.getMono()

        logger.info("GET MONO controller - response: ${Thread.currentThread()}")

        return simpleDtoMono
    }

    @GetMapping("/sample-suspend")
    suspend fun doGetSuspend(): SimpleDto {

        logger.info("GET SUSPEND controller - request entry: ${Thread.currentThread()}")

        val simpleDto = service.getSuspend()

        logger.info("GET SUSPEND controller - response: ${Thread.currentThread()}")

        return simpleDto
    }
}
