package br.com.igorbag.githubsearch.domain

import br.com.igorbag.githubsearch.domain.error.ErrorEntity

sealed class ResultStatus<out T> {
    object Loading : ResultStatus<Nothing>()
    data class Success<out T>(val data: T) : ResultStatus<T>()
    data class Error<out T>(val error: ErrorEntity) : ResultStatus<T>()
}