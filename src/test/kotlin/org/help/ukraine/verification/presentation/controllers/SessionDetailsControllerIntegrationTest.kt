package org.help.ukraine.verification.presentation.controllers

import org.help.ukraine.verification.presentation.jwt.JwtTokenUtil
import org.help.ukraine.verification.tf.configurations.TestSecurityConfiguration.Companion.VERIFIER_USERNAME
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@IntegrationTest
class SessionDetailsControllerIntegrationTest @Autowired constructor(
    override val mockMvc: MockMvc,
    override val jwtTokenUtil: JwtTokenUtil
) : BaseControllerIntegrationTest(mockMvc, jwtTokenUtil) {

    @Test
    fun getDetailsWorksAsExpected() {
        val sessionId = UUID.randomUUID()
        mockMvc
            .perform(
                get("/api/sessions/$sessionId/details")
                    .header("Authorization", "Bearer ${getToken(VERIFIER_USERNAME)}")
                    .header("Content-type", APPLICATION_JSON)
            )
            .andExpect(status().isOk)
    }
}