package com.turo.templateboot.client

import com.turo.templateboot.SimpleDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TemplateBootApi {
    @get:GET("/sample")
    val config: Call<SimpleDto>

    @GET("/sample-async")
    fun getConfigAsync(@Query("delaySeconds") delaySeconds: Long): Call<SimpleDto>
}
