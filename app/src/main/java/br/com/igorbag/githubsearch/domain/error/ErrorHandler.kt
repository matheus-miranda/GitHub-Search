package br.com.igorbag.githubsearch.domain.error

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}

sealed class ErrorEntity {
    object Network : ErrorEntity()
    object NotFound : ErrorEntity()
    object ServiceUnavailable : ErrorEntity()
    object Unknown : ErrorEntity()
}