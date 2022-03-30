package org.help.ukraine.verification.presentation.dtos.requests

data class SessionRequestDto(
    val entrypointId: String,
    val userIdentifier: String,
    val additionalInfo: Map<String, *>
)
