package br.com.igorbag.githubsearch.data.paging

import androidx.paging.PagingSource
import br.com.igorbag.githubsearch.domain.model.UserRepo
import br.com.igorbag.githubsearch.domain.repository.UserRepoRemoteDataSource
import br.com.igorbag.githubsearch.factory.UserRepoFactory
import br.com.igorbag.githubsearch.factory.UserRepoPagingFactory
import br.com.igorbag.githubsearch.initMockkAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepoPagingSourceTest {

    @MockK
    private lateinit var remoteDataSource: UserRepoRemoteDataSource

    private val userRepoPagingSourceFactory = UserRepoPagingFactory()
    private val userRepoFactory = UserRepoFactory()

    private lateinit var repoPagingSource: RepoPagingSource

    @Before
    fun setUp() {
        initMockkAnnotations()
        repoPagingSource = RepoPagingSource(remoteDataSource, "")
    }

    @Test
    fun `when load is called, then return a success load result`() = runTest {
        coEvery { remoteDataSource.fetchRepoList(any(), any()) } returns userRepoPagingSourceFactory.create()

        val result = repoPagingSource.load(
            PagingSource.LoadParams.Refresh(key = null, loadSize = 1, placeholdersEnabled = false)
        )

        val expected = listOf(userRepoFactory.create())
        assertEquals(
            PagingSource.LoadResult.Page(data = expected, prevKey = null, nextKey = 2),
            result
        )
    }

    @Test
    fun `when load is called, then return an error`() = runTest {
        val exception = RuntimeException()
        coEvery { remoteDataSource.fetchRepoList(any(), any()) } throws exception

        val result = repoPagingSource.load(
            PagingSource.LoadParams.Refresh(key = null, loadSize = 1, placeholdersEnabled = false)
        )

        assertEquals(PagingSource.LoadResult.Error<Int, UserRepo>(exception), result)
    }
}
