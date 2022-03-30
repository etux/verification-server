package org.help.ukraine.verification.presentation.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.notNullValue
import org.help.ukraine.verification.presentation.jwt.JwtTokenUtil
import org.help.ukraine.verification.presentation.jwt.TestUserDetails
import org.help.ukraine.verification.tf.configurations.TestSecurityConfiguration
import org.help.ukraine.verification.tf.configurations.TestSecurityConfiguration.Companion.DEFAULT_PASSWORD
import org.help.ukraine.verification.tf.configurations.TestSecurityConfiguration.Companion.DEFAULT_USERNAME
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@IntegrationTest
internal class SessionControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    @Test
    fun `it works`() {
        mockMvc.perform(
            post("/api/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer ${jwtTokenUtil.generate(create())}")
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
            .andExpect(status().isCreated)
            .andExpect(header().exists("location"))
    }

    private fun create() = TestUserDetails(
        username = DEFAULT_USERNAME,
        password = DEFAULT_PASSWORD,
        authorities = listOf(),
        isAccountExpired = false,
        isAccountLocked = false,
        isCredentialsExpired = false,
        isDisabled = false
    )
}