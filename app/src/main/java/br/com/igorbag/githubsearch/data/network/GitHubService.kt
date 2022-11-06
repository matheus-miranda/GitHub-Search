package br.com.igorbag.githubsearch.data.network

import br.com.igorbag.githubsearch.data.network.dto.UserRepoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {
    @GET("users/{username}/repos")
    suspend fun getAllRepositoriesByUser(
        @Path("username") user: String,
        @Query("page") page: Int,
    ): List<UserRepoResponse>
}