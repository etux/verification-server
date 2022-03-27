package org.help.ukraine.verification.business.services

import org.help.ukraine.verification.domain.entities.Language
import org.help.ukraine.verification.domain.entities.Session
import org.help.ukraine.verification.domain.entities.UserMetadata
import org.help.ukraine.verification.domain.entities.UserPreferences
import org.springframework.security.core.userdetails.User
import java.security.Principal
import java.time.Instant
import java.util.*

class SessionCreateRequest(
    private val entrypointId: String,
    val userIdentifier: String,
    val user: User,
    val instant: Instant
) {

    fun toSession(): Session {
        return Session(
            id = UUID.randomUUID(),
            createdBy = user.username,
            createdAt = instant,
            creatorPreferences = UserPreferences(listOf(), listOf(Language("en"))),
            creatorMetadata = UserMetadata(
                userIdentifier = userIdentifier,
                entryPointIdentifier = entrypointId
            )
        )
    }

}
