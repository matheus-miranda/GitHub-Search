package br.com.igorbag.githubsearch.data.remote

import br.com.igorbag.githubsearch.data.network.GitHubService
import br.com.igorbag.githubsearch.data.network.dto.toModel
import br.com.igorbag.githubsearch.domain.ResultStatus
import br.com.igorbag.githubsearch.domain.error.ErrorHandler
import br.com.igorbag.githubsearch.domain.model.UserRepo
import br.com.igorbag.githubsearch.domain.repository.UserRepoRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RetrofitUserRepoDataSource @Inject constructor(
    private val api: GitHubService,
    private val errorHandler: ErrorHandler,
) : UserRepoRemoteDataSource {

    override fun fetchRepoList(username: String): Flow<ResultStatus<List<UserRepo>>> = flow {
        emit(ResultStatus.Loading)
        runCatching {
            val repoList = api.getAllRepositoriesByUser(username).map { it.toModel() }
            emit(ResultStatus.Success(repoList))
        }.onFailure { throwable ->
            emit(ResultStatus.Error(errorHandler.getError(throwable)))
        }
    }
}