package org.help.ukraine.verification.presentation.mappers

import org.help.ukraine.verification.domain.entities.Session
import org.help.ukraine.verification.presentation.dtos.responses.SessionDto
import org.springframework.stereotype.Component

@Component
class SessionResponseMapper : Mapper<Session, SessionDto> {
    override fun map(input: Session) = SessionDto(input.id)
}
