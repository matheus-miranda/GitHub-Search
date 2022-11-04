package br.com.igorbag.githubsearch.data.repository

import br.com.igorbag.githubsearch.domain.repository.LocalStorageDataSource
import br.com.igorbag.githubsearch.domain.repository.StorageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val localStorageDataSource: LocalStorageDataSource,
) : StorageRepository {

    override fun getUser(): Flow<String> {
        return localStorageDataSource.getUser()
    }

    override suspend fun saveUser(user: String) {
        localStorageDataSource.saveUser(user)
    }
}