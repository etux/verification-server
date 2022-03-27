package org.help.ukraine.verification.business.services

import org.help.ukraine.verification.domain.entities.Session
import org.help.ukraine.verification.domain.repositories.SessionRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class SessionServiceImp(
    val sessionRepository: SessionRepository
) : SessionService {

    @PreAuthorize("hasRole('ENTRYPOINT')")
    override fun createSession(
        sessionCreateRequest: SessionCreateRequest
    ): Result<Session> {
        return sessionRepository.findOrCreate(sessionCreateRequest.toSession())
    }
}