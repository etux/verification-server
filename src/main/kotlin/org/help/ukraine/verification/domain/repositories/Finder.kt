package org.help.ukraine.verification.domain.repositories

import org.help.ukraine.verification.business.services.Result

interface Finder<T> {
    fun find(): Result<T>
}
