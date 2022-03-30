package org.help.ukraine.verification.domain.repositories

import org.help.ukraine.verification.business.services.Result
import org.help.ukraine.verification.domain.entities.Session
import java.util.*

interface SessionRepository {
    fun findOrCreate(session: Session): Result<Session>
    fun find(sessionId: UUID): Result<Session>
    fun create(session: Session): Result<Session>
}
