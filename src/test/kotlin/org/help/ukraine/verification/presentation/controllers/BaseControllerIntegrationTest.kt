package org.help.ukraine.verification.presentation.controllers

import org.help.ukraine.verification.presentation.jwt.JwtTokenUtil
import org.help.ukraine.verification.tf.configurations.TestSecurityConfiguration.Companion.TEST_USERS
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.test.web.servlet.MockMvc

@IntegrationTest
abstract class BaseControllerIntegrationTest(
    open val mockMvc: MockMvc,
    open val jwtTokenUtil: JwtTokenUtil
) {

    fun getToken(username: String): String {
        return createToken(
            checkNotNull(TEST_USERS[username]) { "The username does not exist." }
        )
    }

    fun createToken(userDetails: UserDetails): String {
        return jwtTokenUtil.generate(userDetails)
    }
}