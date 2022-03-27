package org.help.ukraine.verification.business.services

import org.help.ukraine.verification.domain.entities.Session

interface SessionService {
    fun createSession(sessionCreateRequest: SessionCreateRequest) : Result<Session>
}