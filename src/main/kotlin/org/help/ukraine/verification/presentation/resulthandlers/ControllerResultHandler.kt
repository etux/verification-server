package org.help.ukraine.verification.presentation.resulthandlers

import org.help.ukraine.verification.business.handlers.ResultHandler
import org.springframework.web.util.UriComponentsBuilder

abstract class  ControllerResultHandler<T,O>(protected open val uriComponentsBuilder: UriComponentsBuilder) : ResultHandler<T, O> {}