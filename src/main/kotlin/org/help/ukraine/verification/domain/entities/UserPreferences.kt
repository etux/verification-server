package org.help.ukraine.verification.domain.entities

class UserPreferences(
    private val channels: List<Channel>,
    private val languages: List<Language>,
) {
    companion object {
        val NON_EXISTING = UserPreferences(channels = listOf(), languages = listOf())
    }
}
