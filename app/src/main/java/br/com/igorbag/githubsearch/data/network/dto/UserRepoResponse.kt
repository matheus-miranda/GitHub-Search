package br.com.igorbag.githubsearch.data.network.dto

import br.com.igorbag.githubsearch.domain.model.UserRepo
import com.google.gson.annotations.SerializedName

data class UserRepoResponse(
    @SerializedName("name") val name: String,
    @SerializedName("html_url") val htmlUrl: String,
)

fun UserRepoResponse.toModel() = UserRepo(
    name = this.name,
    htmlUrl = this.htmlUrl,
)