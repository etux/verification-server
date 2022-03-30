package org.help.ukraine.verification.domain.entities

import java.time.Instant
import java.util.*

data class Session(
    val id: UUID = UUID.randomUUID(),
    val createdBy: String,
    val createdAt: Instant,
    val creatorPreferences: UserPreferences,
    val creatorMetadata: UserMetadata
) {
    companion object {
        val NON_EXISTING = Session(
            id = UUID.fromString("00000000-0000-0000-0000-000000000000"),
            createdBy = "",
            createdAt = Instant.ofEpochMilli(0),
            creatorPreferences = UserPreferences.NON_EXISTING,
            creatorMetadata = UserMetadata.NON_EXISTING
        )
    }
}