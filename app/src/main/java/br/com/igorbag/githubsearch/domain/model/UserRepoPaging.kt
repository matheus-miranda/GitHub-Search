package br.com.igorbag.githubsearch.domain.model

data class UserRepoPaging(
    val page: Int,
    val repoList: List<UserRepo>,
)