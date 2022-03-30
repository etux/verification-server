package org.help.ukraine.verification.presentation.dtos.responses

import java.util.*

data class SessionDto(
    val id: UUID,
    val location: String,
    val signature: String,
    val inputDetailsLocation: String?,
) {
    companion object {
        const val UNSET = "UNSET"
    }
}