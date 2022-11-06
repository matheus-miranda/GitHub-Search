package br.com.igorbag.githubsearch.domain.repository

import br.com.igorbag.githubsearch.domain.model.UserRepoPaging

interface UserRepoRemoteDataSource {
    suspend fun fetchRepoList(username: String, page: Int): UserRepoPaging
}