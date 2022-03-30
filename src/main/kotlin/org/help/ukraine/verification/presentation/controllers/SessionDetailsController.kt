package org.help.ukraine.verification.presentation.controllers

import org.help.ukraine.verification.business.services.SessionService
import org.help.ukraine.verification.domain.entities.InputDetails
import org.help.ukraine.verification.presentation.dtos.InputDetailsDto
import org.help.ukraine.verification.presentation.dtos.responses.SessionDetailsDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping(
    path = ["/api/sessions/{sessionId}/details"],
    consumes = ["application/json"],
    produces = ["application/json"]
)
class SessionDetailsController(
    private val sessionService: SessionService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('VERIFIER')")
    fun getDetails(@PathVariable("sessionId") sessionId: UUID) = map(sessionService.getInputDetails(sessionId))

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('INITIATOR')")
    fun addDetails(
        @PathVariable("sessionId") sessionId: UUID,
        @RequestBody inputDetailsDto: InputDetailsDto
    ) {
       val session = sessionService.addInputDetails(sessionId, map(inputDetailsDto))
        if (session == null) {
            logger.info("No session found [{}].", sessionId)
        }
    }

    private fun map(inputDetailsDto: InputDetailsDto) = InputDetails(
        firstName = inputDetailsDto.firstName,
        middleName = inputDetailsDto.middleName,
        lastName = inputDetailsDto.lastName,
        birthYear = inputDetailsDto.birthYear,
        gender = inputDetailsDto.gender,
        location = null
    )

    private fun map(inputDetails: InputDetails) = InputDetailsDto(
            inputDetails.firstName,
            inputDetails.middleName,
            inputDetails.lastName,
            inputDetails.birthYear,
            inputDetails.gender
        )

    companion object {
        val logger: Logger = LoggerFactory.getLogger(SessionDetailsController::class.java)
    }
}