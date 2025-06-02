package org.help.ukraine.verification.presentation.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.*
import org.help.ukraine.verification.presentation.jwt.JwtTokenUtil
import org.help.ukraine.verification.tf.configurations.TestSecurityConfiguration
import org.help.ukraine.verification.tf.configurations.TestSecurityConfiguration.Companion.create
import org.help.ukraine.verification.tf.configurations.TestSecurityConfiguration.Companion.get
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@IntegrationTest
internal class SessionControllerIntegrationTest @Autowired constructor(
    override val mockMvc: MockMvc,
    override val jwtTokenUtil: JwtTokenUtil
) : BaseControllerIntegrationTest(mockMvc, jwtTokenUtil) {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `it works`() {
        mockMvc.perform(
            post("/api/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(
                    "Authorization",
                    "Bearer ${getToken(TestSecurityConfiguration.ENTRY_POINT_USERNAME)}")
                .content(
                    objectMapper.writeValueAsString(
                        mapOf(
                            "entrypointId" to "test-entry-point-id",
                            "userIdentifier" to "user-identifier",
                            "additionalInfo" to mapOf<String, Any>()
                        )
                    )
                )
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isCreated)
            .andExpect(header().string("location", matchesPattern("http://localhost/api/sessions/[-0-9a-zA-Z]+")))
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.signature", notNullValue()))
            .andExpect(jsonPath("$.location", matchesPattern("http://localhost/api/sessions/[-0-9a-zA-Z]+")))
            .andExpect(jsonPath("$.inputDetailsLocation", matchesPattern("http://localhost:8080/ui/sessions/[-0-9a-zA-Z]+/details")))
    }
}