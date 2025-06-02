package org.help.ukraine.verification.domain.entities

import java.net.URI

data class InputDetails(
    val firstName: String,
    val middleName: String? = null,
    val lastName: String,
    val birthYear: Int,
    val gender: Gender = Gender.UNDEFINED,
    val location: URI? = null
) {
    companion object {
        private const val UNSET: String =  "UNSET"
        val NON_EXISTING = InputDetails(
            firstName = UNSET,
            lastName = UNSET,
            birthYear = 0
        )
    }
}

enum class Gender {
    MALE, FEMALE, OTHER, UNDEFINED
}
