package org.help.ukraine.verification.business.services

import org.help.ukraine.verification.domain.entities.EntryPointMetadata
import org.help.ukraine.verification.domain.entities.UserPreferences
import org.springframework.security.core.userdetails.User

class SessionCreateRequest(
    val entrypointId: String,
    val entrypointUserIdentifier: String,
    val entryPointUser: User
) {

    fun execute(sessionService: SessionService) = sessionService.createSession(
            createdBy = entryPointUser.username,
            initiatorPreferences = UserPreferences(listOf(), listOf()),
            entryPointMetadata = EntryPointMetadata(entrypointUserIdentifier, entrypointId)
        )
}
