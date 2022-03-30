package org.help.ukraine.verification.presentation.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.help.ukraine.verification.presentation.jwt.JwtTokenUtil
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@IntegrationTest
class AuthenticationControllerIntegrationTest @Autowired constructor(
    override val mockMvc: MockMvc,
    override val jwtTokenUtil: JwtTokenUtil
) : BaseControllerIntegrationTest(mockMvc, jwtTokenUtil) {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `authentications works`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/authentications")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        mapOf(
                            "username" to "user",
                            "password" to "password"
                        )
                    )
                )
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isAccepted)
            .andExpect(MockMvcResultMatchers.jsonPath("$.token", Matchers.notNullValue()))
    }
}