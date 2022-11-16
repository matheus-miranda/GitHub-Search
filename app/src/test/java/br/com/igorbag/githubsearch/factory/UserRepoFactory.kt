package br.com.igorbag.githubsearch.factory

import br.com.igorbag.githubsearch.domain.model.UserRepo

class UserRepoFactory {

    fun create() = UserRepo(
        name = "octocat",
        htmlUrl = "https://github.com/octocat"
    )
}
