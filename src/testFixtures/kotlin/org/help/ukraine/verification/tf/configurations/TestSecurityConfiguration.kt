package org.help.ukraine.verification.tf.configurations

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@TestConfiguration
class TestSecurityConfiguration {

    @Bean
    fun userDetailsService(passwordEncoder: PasswordEncoder): UserDetailsService {
        val user: UserDetails = User.builder()
            .passwordEncoder(passwordEncoder::encode)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(user)
    }

    companion object {
        const val DEFAULT_USERNAME = "user"
        const val DEFAULT_PASSWORD = "password"
    }
}