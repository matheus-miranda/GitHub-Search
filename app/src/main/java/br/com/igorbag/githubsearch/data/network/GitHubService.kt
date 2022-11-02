package br.com.igorbag.githubsearch.data.network

import br.com.igorbag.githubsearch.data.network.dto.UserRepoResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("users/{username}/repos")
    suspend fun getAllRepositoriesByUser(@Path("username") user: String): List<UserRepoResponse>
}