package org.help.ukraine.verification.presentation.resulthandlers

import org.help.ukraine.verification.domain.entities.Session
import org.help.ukraine.verification.presentation.dtos.responses.SessionDto
import org.help.ukraine.verification.presentation.mappers.Mapper
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

class SessionResultHandler(
    override val uriComponentsBuilder: UriComponentsBuilder,
    val mapper: Mapper<Session, SessionDto>
) :
    ControllerResultHandler<Session, ResponseEntity<*>>(uriComponentsBuilder) {

    override fun notFound(): ResponseEntity<*> = ResponseEntity.notFound().build<Nothing>()

    override fun read(result: Session): ResponseEntity<*> = ResponseEntity.ok(mapper.map(result))

    override fun updated(result: Session): ResponseEntity<*> = ResponseEntity.accepted().body(mapper.map(result))

    override fun deleted(result: Session): ResponseEntity<*> = ResponseEntity.accepted().build<Nothing>()

    override fun created(result: Session): ResponseEntity<*> = ResponseEntity.created(
        uriComponentsBuilder.path(result.id.toString()).build().toUri()
    ).body(mapper.map(result))
}