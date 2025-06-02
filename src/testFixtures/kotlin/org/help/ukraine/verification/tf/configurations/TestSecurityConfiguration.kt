package org.help.ukraine.verification.tf.configurations

import org.help.ukraine.verification.presentation.jwt.TestUserDetails
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@TestConfiguration
class TestSecurityConfiguration {

    @Bean
    fun userDetailsService(passwordEncoder: PasswordEncoder): UserDetailsService {
        return InMemoryUserDetailsManager(
            *TEST_USERS.values.map { buildUser(it, passwordEncoder) }.toTypedArray()
        )
    }

    private fun buildUser(
        userDetails: TestUserDetails,
        passwordEncoder: PasswordEncoder
    ): UserDetails = User.builder()
            .passwordEncoder(passwordEncoder::encode)
            .username(userDetails.username)
            .password(userDetails.password)
            .authorities(*userDetails.authorities.map(GrantedAuthority::getAuthority).toTypedArray())
            .build()

    companion object {
        const val ENTRY_POINT_USERNAME = "user"
        private const val ENTRY_POINT_PASSWORD = "password"
        const val VERIFIER_USERNAME = "john.doe"
        const val INITIATOR_USERNAME = "max.mustermann"

        val TEST_USERS = mapOf(
            ENTRY_POINT_USERNAME to create(
                username = ENTRY_POINT_USERNAME, password = ENTRY_POINT_PASSWORD,
                listOf(GrantedAuthority { "ROLE_ENTRYPOINT" })
            ),
            VERIFIER_USERNAME to create(
                username = VERIFIER_USERNAME, password = "johndoespassword",
                listOf(GrantedAuthority { "ROLE_VERIFIER" })
            ),
            INITIATOR_USERNAME to create(
                username = INITIATOR_USERNAME, password = "maxspassword",
                listOf(GrantedAuthority { "ROLE_INITIATOR" })
            )
        )

        fun create(
            username: String = ENTRY_POINT_USERNAME,
            password: String = ENTRY_POINT_PASSWORD,
            authorities: Collection<GrantedAuthority> = listOf(),
            isAccountExpired: Boolean = false,
            isAccountLocked: Boolean = false,
            isCredentialsExpired: Boolean = false,
            isDisabled: Boolean = false
        ) = TestUserDetails (
            username = username,
            password = password,
            authorities = authorities,
            isAccountExpired = isAccountExpired,
            isAccountLocked = isAccountLocked,
            isCredentialsExpired = isCredentialsExpired,
            isDisabled = isDisabled
        )

        fun get(username: String): UserDetails = TEST_USERS[username]!!
    }
}