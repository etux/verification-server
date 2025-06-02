package org.help.ukraine.verification.presentation.dtos

import org.help.ukraine.verification.domain.entities.Gender

data class InputDetailsDto(
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val birthYear: Int,
    val gender: Gender = Gender.UNDEFINED
)
