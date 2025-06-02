package org.help.ukraine.verification.domain.entities

import java.time.Instant
import java.util.*

data class Session(
    val id: UUID = UUID.randomUUID(),
    val createdBy: String,
    val createdAt: Instant,
    val expiresAt: Instant,
    val initiatorPreferences: UserPreferences,
    val entryPointMetadata: EntryPointMetadata,
    val signature: String,
    val inputDetails: InputDetails,
) {
    companion object {
        val NON_EXISTING = Session(
            id = UUID.fromString("00000000-0000-0000-0000-000000000000"),
            createdBy = "",
            createdAt = Instant.ofEpochMilli(0),
            expiresAt = Instant.ofEpochMilli(0),
            initiatorPreferences = UserPreferences.NON_EXISTING,
            entryPointMetadata = EntryPointMetadata.NON_EXISTING,
            signature = "",
            inputDetails = InputDetails.NON_EXISTING
        )
    }
}