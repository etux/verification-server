package org.help.ukraine.verification.presentation.mappers

import org.help.ukraine.verification.business.services.SessionCreateRequest
import org.help.ukraine.verification.presentation.dtos.requests.SessionRequestDto
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.time.Clock

@Component
class SessionRequestMapper(private val clock: Clock) : Mapper<Pair<SessionRequestDto, User>, SessionCreateRequest> {
    override fun map(input: Pair<SessionRequestDto, User>) = SessionCreateRequest(
        entrypointId = input.first.entrypointId,
        entrypointUserIdentifier = input.first.userIdentifier,
        entryPointUser = input.second
    )
}