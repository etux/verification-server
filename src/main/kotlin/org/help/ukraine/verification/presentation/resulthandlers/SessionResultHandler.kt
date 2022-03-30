package org.help.ukraine.verification.presentation.resulthandlers

import org.help.ukraine.verification.domain.entities.Session
import org.help.ukraine.verification.presentation.dtos.responses.SessionDto
import org.help.ukraine.verification.presentation.mappers.Mapper
import org.help.ukraine.verification.presentation.mappers.SessionResponseMapper
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

class SessionResultHandler(
    private val path: String,
    uriComponentsBuilder: UriComponentsBuilder,
    private val mapper: SessionResponseMapper
) :
    ControllerResultHandler<Session, ResponseEntity<*>>(uriComponentsBuilder) {

    override fun notFound(): ResponseEntity<*> = ResponseEntity.notFound().build<Nothing>()

    override fun read(result: Session): ResponseEntity<*> = ResponseEntity.ok(mapper.map(result))

    override fun updated(result: Session): ResponseEntity<*> = ResponseEntity.accepted().body(mapper.map(result))

    override fun deleted(result: Session): ResponseEntity<*> = ResponseEntity.accepted().build<Nothing>()

    override fun created(result: Session): ResponseEntity<*> {
        val toUri = uriComponentsBuilder.path("$path/{uuid}").buildAndExpand(result.id).toUri()
        return ResponseEntity.created(
            toUri
        ).body(mapper.map(result).let { it.copy(location = toUri.toString()) })
    }
}