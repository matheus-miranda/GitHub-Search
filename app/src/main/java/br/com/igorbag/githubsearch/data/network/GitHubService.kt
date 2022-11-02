package br.com.igorbag.githubsearch.data.network

import br.com.igorbag.githubsearch.domain.model.UserRepo
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    @GET("users/{username}/repos")
    fun getAllRepositoriesByUser(@Path("username") user: String): Flow<List<UserRepo>>
}