package org.help.ukraine.verification.presentation.controllers

import org.help.ukraine.verification.business.services.SessionService
import org.help.ukraine.verification.presentation.controllers.SessionController.Companion.CONTROLLER_PATH
import org.help.ukraine.verification.presentation.dtos.requests.SessionRequestDto
import org.help.ukraine.verification.presentation.mappers.SessionRequestMapper
import org.help.ukraine.verification.presentation.mappers.SessionResponseMapper
import org.help.ukraine.verification.presentation.resulthandlers.SessionResultHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
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
@RequestMapping(
    path = [CONTROLLER_PATH],
    consumes = ["application/json"],
    produces = ["application/json"]
)
class SessionController(
    private val sessionService: SessionService,
    private val sessionMapper: SessionResponseMapper,
    private val sessionRequestMapper: SessionRequestMapper
) {

    @PostMapping
    @PreAuthorize("hasRole('ENTRYPOINT')")
    fun create (
        uriComponentsBuilder: UriComponentsBuilder,
        @RequestBody sessionRequest: SessionRequestDto,
        @CurrentSecurityContext(expression = "authentication") authentication: Authentication
    ) : ResponseEntity<*> = sessionService
            .createSession(mapRequest(sessionRequest, authentication))
            .handle(handleResult(uriComponentsBuilder))

    private fun handleResult(uriComponentsBuilder: UriComponentsBuilder) =
        SessionResultHandler(CONTROLLER_PATH, uriComponentsBuilder, sessionMapper)

    private fun mapRequest(
        sessionRequest: SessionRequestDto,
        authentication: Authentication
    ) = sessionRequestMapper.map(Pair(sessionRequest, authentication.principal as User))

    companion object {
        const val CONTROLLER_PATH = "/api/sessions"
    }
}