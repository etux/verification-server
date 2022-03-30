package org.help.ukraine.verification.presentation.jwt

import io.jsonwebtoken.ExpiredJwtException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import java.time.Clock
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.security.auth.login.AccountException

internal class JwtTokenUtilTest {

    private lateinit var subject: JwtTokenUtil

    private lateinit var userDetailsService: UserDetailsService

    private lateinit var clock: Clock

    @BeforeEach
    fun setUp() {
        userDetailsService = mock { }
        clock = mock { }
        whenever(clock.instant()).thenReturn(Instant.now())
        subject = JwtTokenUtil(userDetailsService, "secret", 1000, clock)
    }

    @Test
    fun `generates valid token`() {
        val userDetails = user()
        whenever(userDetailsService.loadUserByUsername(userDetails.username)).thenReturn(userDetails)

        val value = subject.generate(userDetails)
        assertThat(value).isNotEmpty

        val token = subject.validate(value)
        assertThat(token.getUserDetails()).isEqualTo(userDetails)
    }

    @Test
    fun `generate for invalid details throws exception`() {
        val userDetails = user(isAccountExpired = true)
        whenever(userDetailsService.loadUserByUsername(userDetails.username)).thenReturn(userDetails)

        assertThrows<AccountException> {
            subject.generate(userDetails)
        }
    }

    @Test
    fun `validate of expired token throw exception`() {
        val userDetails = user()
        whenever(userDetailsService.loadUserByUsername(userDetails.username)).thenReturn(userDetails)

        val token = subject.generate(userDetails)

        whenever(clock.instant()).thenReturn(Instant.now().plus(1000, ChronoUnit.MILLIS))

        assertThrows<ExpiredJwtException> {
            subject.validate(token)
        }
    }

    class TestGrantedAuthority(private val authority: String): GrantedAuthority {
        override fun getAuthority() = authority
    }

    companion object {
        fun user(
            username: String = "john.doe",
            password: String = "password",
            authorities: Collection<GrantedAuthority> = listOf(),
            isAccountExpired: Boolean = false,
            isAccountLocked: Boolean = false,
            isCredentialsExpired: Boolean = false,
            isDisabled: Boolean = false
        ) = TestUserDetails(
            username = username,
            password = password,
            authorities = authorities,
            isAccountExpired = isAccountExpired,
            isAccountLocked = isAccountLocked,
            isCredentialsExpired = isCredentialsExpired,
            isDisabled = isDisabled
        )

        fun authority(authority: String) = TestGrantedAuthority(authority)
    }
}