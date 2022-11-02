package br.com.igorbag.githubsearch.data.repository

import br.com.igorbag.githubsearch.domain.ResultStatus
import br.com.igorbag.githubsearch.domain.model.UserRepo
import br.com.igorbag.githubsearch.domain.repository.UserRepoRemoteDataSource
import br.com.igorbag.githubsearch.domain.repository.UserRepoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepoRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRepoRemoteDataSource
) : UserRepoRepository {

    override fun fetchRepoList(username: String): Flow<ResultStatus<List<UserRepo>>> {
        return remoteDataSource.fetchRepoList(username)
    }
}