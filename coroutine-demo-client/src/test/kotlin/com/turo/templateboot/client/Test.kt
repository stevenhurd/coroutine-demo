package com.turo.coroutinedemo.client

import com.turo.coroutinedemo.SimpleDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * this is just a simple placeholder test that allows for easy confirmation that your application is running.
 *
 * Please remove if/when it has stopped providing value.
 */
object Test {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val baseUrl = "http://localhost:8080"

        val client = CoroutineDemoClientBuilder.start()
            .setBaseUrl(baseUrl)
            .setUserAgent("test", "test")
            .build()

        client.config.enqueue(object : Callback<SimpleDto> {
            override fun onResponse(call: Call<SimpleDto>, response: Response<SimpleDto>) {
                System.out.println("Response: " + response.body())
            }

            override fun onFailure(call: Call<SimpleDto>, t: Throwable) {
                t.printStackTrace()
            }
        })
        Thread.sleep(1000)
        client.config.enqueue(object : Callback<SimpleDto> {
            override fun onResponse(call: Call<SimpleDto>, response: Response<SimpleDto>) {
                System.out.println("Response: " + response.body())
            }

            override fun onFailure(call: Call<SimpleDto>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}
