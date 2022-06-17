package com.turo.templateboot

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class SampleIntegrationTest(@Autowired val mockMvc: MockMvc) {

    @Test
    fun `Sample URL should return HTTP 200`() {
        mockMvc.perform(get("/sample").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }

    @Test
    fun `Invalid URL should returns HTTP 404`() {
        mockMvc.perform(get("/invalid").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError)
    }
}
