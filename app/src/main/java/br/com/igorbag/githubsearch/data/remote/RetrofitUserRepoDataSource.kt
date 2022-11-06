package br.com.igorbag.githubsearch.data.remote

import br.com.igorbag.githubsearch.data.network.GitHubService
import br.com.igorbag.githubsearch.data.network.dto.toModel
import br.com.igorbag.githubsearch.domain.model.UserRepoPaging
import br.com.igorbag.githubsearch.domain.repository.UserRepoRemoteDataSource
import javax.inject.Inject

class RetrofitUserRepoDataSource @Inject constructor(
    private val api: GitHubService,
) : UserRepoRemoteDataSource {

    override suspend fun fetchRepoList(username: String, page: Int): UserRepoPaging {
        val repoList = api.getAllRepositoriesByUser(username, page).map { it.toModel() }
        return UserRepoPaging(page, repoList)
    }
}