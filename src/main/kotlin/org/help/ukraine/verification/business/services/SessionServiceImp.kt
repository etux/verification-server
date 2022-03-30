package org.help.ukraine.verification.business.services

import org.help.ukraine.verification.business.configuration.settings.SessionServiceSettings
import org.help.ukraine.verification.domain.entities.EntryPointMetadata
import org.help.ukraine.verification.domain.entities.InputDetails
import org.help.ukraine.verification.domain.entities.Session
import org.help.ukraine.verification.domain.entities.UserPreferences
import org.help.ukraine.verification.domain.repositories.SessionRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.time.Clock
import java.util.*

@Component
@EnableConfigurationProperties(SessionServiceSettings::class)
class SessionServiceImp(
    private val settings: SessionServiceSettings,
    private val sessionRepository: SessionRepository,
    private val clock: Clock
) : SessionService {

    @PreAuthorize("hasRole('ENTRYPOINT')")
    override fun createSession(
        sessionCreateRequest: SessionCreateRequest
    ): Result<Session> {
        return findOrCreate(
            { sessionRepository.findByUsername(sessionCreateRequest.entrypointUserIdentifier) },
            { sessionRepository.create(sessionCreateRequest.execute(this)) }
        )
    }

    override fun createSession(
        createdBy: String,
        initiatorPreferences: UserPreferences,
        entryPointMetadata: EntryPointMetadata
    ) : Session {
        val sessionId = UUID.randomUUID()
        return Session (
                id = sessionId,
                createdBy = createdBy,
                createdAt = clock.instant(),
                expiresAt = expiresAt(),
                initiatorPreferences = initiatorPreferences,
                entryPointMetadata = entryPointMetadata,
                signature = sign(listOf(entryPointMetadata.userIdentifier, entryPointMetadata.entryPointIdentifier, sessionId)),
                inputDetails = InputDetails.NON_EXISTING.copy(location = getInputDetailsLocation(entryPointMetadata.userIdentifier, sessionId))
        )
    }

    private fun findOrCreate(
        finder: () -> Session?,
        creator: () -> Session
    ): Result<Session> {
        return finder()
            ?.let { Result.read(it) }
            ?: Result.created(creator())
    }

    override fun getInputDetails(sessionId: UUID): InputDetails {
        return sessionRepository.find(sessionId)?.inputDetails ?: InputDetails.NON_EXISTING
    }

    override fun addInputDetails(
        sessionId: UUID,
        inputDetails: InputDetails
    ): Session? {
        return sessionRepository.find(sessionId)
            ?.also { it.copy(inputDetails = inputDetails) }
            ?.also { sessionRepository.update(it) }
    }

    fun expiresAt() =  clock.instant().plusMillis(EXPIRATION_TIME_IN_MILLIS)

    private fun sign(
        fields: List<Any>,
    ): String {
        return fields.joinToString(":") { it.toString() }
    }

    private fun getInputDetailsLocation(entrypointId: String, sessionId: UUID): URI {
        return UriComponentsBuilder
            .fromHttpUrl(settings.inputDetails)
            .buildAndExpand(sessionId)
            .toUri()
    }

    companion object {
        const val EXPIRATION_TIME_IN_MILLIS: Long = 24 * 60 * 60 * 1000
        val logger: Logger = LoggerFactory.getLogger(SessionServiceImp::class.java)
    }
}