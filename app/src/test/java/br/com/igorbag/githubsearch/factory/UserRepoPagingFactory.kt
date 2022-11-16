package br.com.igorbag.githubsearch.factory

import br.com.igorbag.githubsearch.domain.model.UserRepo
import br.com.igorbag.githubsearch.domain.model.UserRepoPaging

class UserRepoPagingFactory {

    fun create() = UserRepoPaging(
        page = 1,
        repoList = listOf(
            UserRepo(
                name = "octocat",
                htmlUrl = "https://github.com/octocat"
            )
        )
    )
}
