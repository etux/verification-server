package org.help.ukraine.verification.domain.repositories

import org.help.ukraine.verification.domain.entities.Session
import java.util.*

interface SessionRepository {
    fun find(sessionId: UUID): Session?
    fun create(session: Session): Session
    fun findByUsername(username: String): Session?
    fun update(session: Session): Session
}
