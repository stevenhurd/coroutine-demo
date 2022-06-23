package com.turo.coroutinedemo

import reactor.core.publisher.Mono

interface SimpleService {
    fun get(): SimpleDto
    fun getMono(): Mono<SimpleDto>
    suspend fun getSuspend(): SimpleDto
}
