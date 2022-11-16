package br.com.igorbag.githubsearch.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import br.com.igorbag.githubsearch.MainCoroutineRule
import br.com.igorbag.githubsearch.domain.repository.StorageRepository
import br.com.igorbag.githubsearch.domain.repository.UserRepoRepository
import br.com.igorbag.githubsearch.factory.UserRepoFactory
import br.com.igorbag.githubsearch.initMockkAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @RelaxedMockK
    private lateinit var userRepoRepository: UserRepoRepository

    @RelaxedMockK
    private lateinit var storageRepository: StorageRepository

    private val userRepoFactory = UserRepoFactory()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        initMockkAnnotations()
        viewModel = MainViewModel(userRepoRepository, storageRepository)
    }

    @Test
    fun `when calling repository list data, then validate the value is not null`() {
        every { userRepoRepository.fetchRepoList(any()) } returns flowOf(PagingData.from(
            listOf(
                userRepoFactory.create()
            )
        )).asLiveData()

        val result = viewModel.repoListData

        assertNotNull(result)
    }

    @Test
    fun `when searching for a user name, then should save it`() = runTest {
        val testUser = "user"
        every { storageRepository.getUser() } returns flowOf(testUser)

        viewModel.saveUserName(String())
        viewModel.retrieveSavedUserName()

        assertEquals("user", viewModel.savedUserName.value)
    }
}
