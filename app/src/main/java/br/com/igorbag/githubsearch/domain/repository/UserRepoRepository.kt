package br.com.igorbag.githubsearch.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import br.com.igorbag.githubsearch.domain.model.UserRepo

interface UserRepoRepository {
    fun fetchRepoList(username: String): LiveData<PagingData<UserRepo>>
}