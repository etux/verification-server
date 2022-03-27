package org.help.ukraine.verification.domain.entities

data class UserMetadata(
    val userIdentifier: String,
    val entryPointIdentifier: String,
    val geolocation: String? = null,
    val ip: String? = null
) {
    companion object {
        val NON_EXISTING = UserMetadata("", "")
    }
}