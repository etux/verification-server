package org.help.ukraine.verification.business.handlers

interface ResultHandler<T, O> {
    fun notFound(): O
    fun read(result: T) : O
    fun updated(result: T) : O
    fun deleted(result: T) : O
    fun created(result: T) : O
}