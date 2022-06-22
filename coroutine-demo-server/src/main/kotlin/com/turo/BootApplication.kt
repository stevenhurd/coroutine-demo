package com.turo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
open class BootApplication

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<BootApplication>(*args)
}
