package org.help.ukraine.verification.presentation.jwt

import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import java.time.Instant
import java.util.*
import javax.security.auth.login.AccountExpiredException
import javax.security.auth.login.AccountLockedException
import javax.security.auth.login.AccountNotFoundException

class JwtTokenUtil(
    private val userDetailsService: UserDetailsService,
    private val secret: String,
    private val expirationTimeMillis: Long = EXPIRATION_TIME,
    private val clock: java.time.Clock
) {

    fun generate(userDetails: UserDetails): String {
        return validate(userDetails).let {
            Jwts
                .builder()
                .setClaims(mutableMapOf())
                .setSubject(it.username)
                .setIssuedAt(dateFromClock())
                .setExpiration(dateFromClock { instant -> instant.plusMillis(expirationTimeMillis) })
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()
        }
    }

    fun validate(token: String): Token {
        return Token(
            value = token,
            jwsClaims = Jwts.parser().setSigningKey(secret).setClock { dateFromClock() }.parseClaimsJws(token),
            userDetails = { validate(userDetailsService.loadUserByUsername(getUsername(it))) }
        )
    }

    fun dateFromClock(instantModifier: (Instant) -> Instant = { it }) = Date.from(instantModifier(clock.instant()))

    private fun validate(userDetails: UserDetails): UserDetails {
        if (!userDetails.isAccountNonExpired) {
            throw AccountExpiredException("The account is expired.")
        }
        if (!userDetails.isAccountNonLocked) {
            throw AccountLockedException("The account is locked.")
        }
        if(!userDetails.isEnabled) {
            throw AccountNotFoundException("The account is not enabled.")
        }
        if(!userDetails.isCredentialsNonExpired) {
            throw CredentialsExpiredException("Credentials are expired.")
        }
        return userDetails
    }

    private fun getUsername(token: Jws<Claims>) = getClaimFromToken(token) { it.body.subject }

    private fun <T> getClaimFromToken(token: Jws<Claims>, extractor: (Jws<Claims>) -> T) = extractor(token)

    companion object {
        const val EXPIRATION_TIME: Long = 5 * 60 * 1000
    }

    class Token(
        private val value: String,
        private val jwsClaims: Jws<Claims>,
        private val userDetails: (Jws<Claims>) -> UserDetails
    ) {
        fun getUserDetails() = userDetails(jwsClaims)
    }
}