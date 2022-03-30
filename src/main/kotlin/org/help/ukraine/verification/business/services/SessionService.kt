package org.help.ukraine.verification.business.services

import org.help.ukraine.verification.domain.entities.EntryPointMetadata
import org.help.ukraine.verification.domain.entities.InputDetails
import org.help.ukraine.verification.domain.entities.Session
import org.help.ukraine.verification.domain.entities.UserPreferences
import java.net.URI
import java.time.Instant
import java.util.UUID

interface SessionService {
    fun createSession(sessionCreateRequest: SessionCreateRequest) : Result<Session>
    fun createSession(
        createdBy: String,
        initiatorPreferences: UserPreferences,
        entryPointMetadata: EntryPointMetadata
    ): Session
    fun getInputDetails(sessionId: UUID): InputDetails
    fun addInputDetails(sessionId: UUID, map: InputDetails): Session?
}