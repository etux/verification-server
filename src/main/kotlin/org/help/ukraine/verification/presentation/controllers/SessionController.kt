package org.help.ukraine.verification.presentation.controllers

import org.help.ukraine.verification.business.services.SessionService
import org.help.ukraine.verification.presentation.dtos.requests.SessionRequestDto
import org.help.ukraine.verification.presentation.mappers.SessionRequestMapper
import org.help.ukraine.verification.presentation.mappers.SessionResponseMapper
import org.help.ukraine.verification.presentation.resulthandlers.SessionResultHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.CurrentSecurityContext
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/api/sessions")
class SessionController(
    private val sessionService: SessionService,
    private val sessionMapper: SessionResponseMapper,
    private val sessionRequestMapper: SessionRequestMapper
) {

    @PostMapping(
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    @PreAuthorize("hasRole('EntryPoint')")
    fun create (
        uriComponentsBuilder: UriComponentsBuilder,
        @RequestBody sessionRequest: SessionRequestDto,
        @CurrentSecurityContext(expression = "authentication") authentication: Authentication
    ) = sessionService
            .createSession(mapRequest(sessionRequest, authentication))
            .handle(handleResult(uriComponentsBuilder))

    private fun handleResult(uriComponentsBuilder: UriComponentsBuilder) =
        SessionResultHandler(uriComponentsBuilder, sessionMapper)

    private fun mapRequest(
        sessionRequest: SessionRequestDto,
        authentication: Authentication
    ) = sessionRequestMapper.map(Pair(sessionRequest, authentication.principal as User))
}