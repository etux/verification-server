package org.help.ukraine.verification.presentation.controllers

import org.help.ukraine.verification.presentation.dtos.requests.AuthenticationDto
import org.help.ukraine.verification.presentation.dtos.responses.TokenDto
import org.help.ukraine.verification.presentation.jwt.JwtTokenUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/authentications")
class AuthenticationController(
    private val userDetailsService: UserDetailsService,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenUtil: JwtTokenUtil
) {

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun authenticate(
        @RequestBody authenticationDetails: AuthenticationDto
    ): TokenDto {
        return authenticationDetails.authenticated { token() }
    }

    private fun <T> AuthenticationDto.authenticated(execute: AuthenticationDto.() -> T): T {
        authenticate()
        return execute()
    }

    private fun AuthenticationDto.authenticate() = authenticationManager.authenticate(
        UsernamePasswordAuthenticationToken(username, password)
    ).also { logger.info("User [{}] authenticated.", username) }

    private fun AuthenticationDto.token() = jwtTokenUtil.generate(getUserDetails()).toTokenDto()

    private fun AuthenticationDto.getUserDetails() = userDetailsService.loadUserByUsername(username)

    fun String.toTokenDto() = TokenDto(this)

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AuthenticationController::class.java)
    }
}