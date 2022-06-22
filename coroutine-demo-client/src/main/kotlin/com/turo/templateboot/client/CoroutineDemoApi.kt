package com.turo.coroutinedemo.client

import com.turo.coroutinedemo.SimpleDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CoroutineDemoApi {
    @get:GET("/sample")
    val config: Call<SimpleDto>

    @GET("/sample-async")
    fun getConfigAsync(@Query("delaySeconds") delaySeconds: Long): Call<SimpleDto>
}
