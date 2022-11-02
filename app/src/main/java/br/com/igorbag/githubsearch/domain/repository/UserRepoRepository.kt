package br.com.igorbag.githubsearch.domain.repository

import br.com.igorbag.githubsearch.domain.ResultStatus
import br.com.igorbag.githubsearch.domain.model.UserRepo
import kotlinx.coroutines.flow.Flow

interface UserRepoRepository {
    fun fetchRepoList(): Flow<ResultStatus<List<UserRepo>>>
}