package br.com.igorbag.githubsearch.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import br.com.igorbag.githubsearch.data.Constants.ITEMS_PER_PAGE
import br.com.igorbag.githubsearch.data.paging.RepoPagingSource
import br.com.igorbag.githubsearch.domain.model.UserRepo
import br.com.igorbag.githubsearch.domain.repository.UserRepoRemoteDataSource
import br.com.igorbag.githubsearch.domain.repository.UserRepoRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepoRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRepoRemoteDataSource,
) : UserRepoRepository {

    override fun fetchRepoList(username: String): LiveData<PagingData<UserRepo>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                maxSize = 90,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { RepoPagingSource(remoteDataSource, username) }
        ).flow.map { pagingData ->
            pagingData.map {
                UserRepo(it.name, it.htmlUrl)
            }
        }.asLiveData()
    }
}