package org.help.ukraine.verification.presentation.configurations

import org.help.ukraine.verification.presentation.jwt.JwtTokenUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import java.time.Clock

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun jwtTokenUtil(
        userDetailsService: UserDetailsService,
        @Value("\${jwt.secret}") secret: String,
        @Value("\${jwt.token.expiration-time-millis}") expirationTimeMillis: Long = 30 * 60 * 1000,
        applicationClock: Clock
    ) = JwtTokenUtil(
        userDetailsService,
        secret,
        expirationTimeMillis,
        applicationClock
    )
}