package br.com.igorbag.githubsearch.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.igorbag.githubsearch.data.Constants.REPO_STARTING_PAGE_INDEX
import br.com.igorbag.githubsearch.domain.model.UserRepo
import br.com.igorbag.githubsearch.domain.repository.UserRepoRemoteDataSource
import retrofit2.HttpException
import java.io.IOException

class RepoPagingSource(
    private val remoteDataSource: UserRepoRemoteDataSource,
    private val query: String,
) : PagingSource<Int, UserRepo>() {

    override fun getRefreshKey(state: PagingState<Int, UserRepo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(REPO_STARTING_PAGE_INDEX) ?: anchorPage?.nextKey?.minus(REPO_STARTING_PAGE_INDEX)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserRepo> {
        return try {
            val nextPageNumber = params.key ?: REPO_STARTING_PAGE_INDEX
            val response = remoteDataSource.fetchRepoList(query, nextPageNumber)

            LoadResult.Page(
                data = response.repoList,
                prevKey = if (nextPageNumber == REPO_STARTING_PAGE_INDEX) null else nextPageNumber - 1,
                nextKey = if (response.repoList.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}