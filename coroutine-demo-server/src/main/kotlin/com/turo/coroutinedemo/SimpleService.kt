package com.turo.coroutinedemo

import reactor.core.publisher.Mono
import java.util.concurrent.CompletableFuture

interface SimpleService {
    fun get(): SimpleDto
    fun getAsync(delaySeconds: Long): CompletableFuture<SimpleDto>

    fun getMono(): Mono<SimpleDto>
    suspend fun getSuspend(): SimpleDto
}
