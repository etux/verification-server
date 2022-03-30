package org.help.ukraine.verification.presentation.mappers

interface Mapper<T, U> {
    fun map(input: T): U
}
