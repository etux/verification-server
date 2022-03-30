package org.help.ukraine.verification.presentation.dtos.responses

import org.help.ukraine.verification.domain.entities.Gender

class SessionDetailsDto(
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val birthYear: Int,
    val gender: Gender
)