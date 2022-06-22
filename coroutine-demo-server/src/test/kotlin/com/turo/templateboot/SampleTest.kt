package com.turo.coroutinedemo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Dummy test class to generate test report on GHA
 */
class SampleTest {

    @Test
    fun `Dividing by zero should throw ArithmeticException `() {
        Assertions.assertThrows(ArithmeticException::class.java) {
            10 / 0
        }
    }
}
