package org.help.ukraine.verification.domain.repositories

import org.help.ukraine.verification.domain.entities.Session
import org.springframework.stereotype.Component
import java.util.UUID
import org.help.ukraine.verification.business.services.Result

@Component
class InMemorySessionRepository(
    private val sessions: MutableMap<UUID, Session> = mutableMapOf()
) : SessionRepository {

    override fun findOrCreate(session: Session): Result<Session> {
        return find(session.id).let {
            when(it) {
                is Result.NotFoundResult -> create(session)
                else -> it
            }
        }
    }

    override fun find(sessionId: UUID): Result<Session> {
        return sessions[sessionId]?.let { Result.read(it) } ?: Result.notFound(Session.NON_EXISTING)
    }

    override fun create(session: Session): Result<Session> {
        sessions[session.id] = session
        return Result.created(session)
    }
}