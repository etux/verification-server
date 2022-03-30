package org.help.ukraine.verification.domain.repositories

import org.help.ukraine.verification.domain.entities.Session
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class InMemorySessionRepository(
    private val sessions: MutableMap<UUID, Session> = mutableMapOf()
) : SessionRepository {

    override fun find(sessionId: UUID): Session? {
        return sessions[sessionId]
    }

    override fun create(session: Session): Session {
        sessions[session.id] = session
        return session
    }

    override fun update(session: Session): Session {
        sessions[session.id] = session
        return session
    }

    override fun findByUsername(username: String): Session? {
        return sessions.values.firstOrNull { it.entryPointMetadata.userIdentifier == username }
    }
}