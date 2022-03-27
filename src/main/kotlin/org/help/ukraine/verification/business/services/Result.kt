package org.help.ukraine.verification.business.services

import org.help.ukraine.verification.business.handlers.ResultHandler

sealed class Result<T>(open val result: T) {

    class NotFoundResult<T>(result: T) : Result<T>(result) {
        override fun <O> handle(handler: ResultHandler<T, O>): O {
            return handler.notFound()
        }
    }

    class ReadResult<T>(override val result: T): Result<T>(result) {
        override fun <O> handle(handler: ResultHandler<T, O>): O {
            return handler.read(result)
        }
    }

    class CreatedResult<T>(override val result: T): Result<T>(result) {
        override fun <O> handle(handler: ResultHandler<T, O>): O {
            return handler.created(result)
        }
    }
    class UpdatedResult<T>(override val result: T): Result<T>(result) {
        override fun <O> handle(handler: ResultHandler<T, O>): O {
            return handler.updated(result)
        }
    }

    class DeletedResult<T>(override val result: T): Result<T>(result) {
        override fun <O> handle(handler: ResultHandler<T, O>): O {
            return handler.deleted(result)
        }
    }

    abstract fun <O> handle(handler: ResultHandler<T, O>): O

    companion object {
        fun <T> read(result: T) = ReadResult(result)
        fun <T> created(result: T) = CreatedResult(result)
        fun <T> updated(result: T) = UpdatedResult(result)
        fun <T> deleted(result: T) = DeletedResult(result)
        fun <T> notFound(result: T) = NotFoundResult(result)
    }
}