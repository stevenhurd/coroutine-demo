package com.turo.coroutinedemo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
class SimpleController(private val service: SimpleService) {

    @GetMapping("/sample")
    fun doGet(): SimpleDto {
        return service.get()
    }

    @GetMapping("/sample-async")
    fun doGetAsync(
        @RequestParam(defaultValue = "5")
        delaySeconds: Long
    ): CompletableFuture<SimpleDto> {
        return service.getAsync(delaySeconds)
    }
}
