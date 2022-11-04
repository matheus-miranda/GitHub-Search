package br.com.igorbag.githubsearch.di

import br.com.igorbag.githubsearch.data.local.DataStoreLocalDataSource
import br.com.igorbag.githubsearch.data.repository.StorageRepositoryImpl
import br.com.igorbag.githubsearch.domain.repository.LocalStorageDataSource
import br.com.igorbag.githubsearch.domain.repository.StorageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageRepositoryModule {

    @Binds
    abstract fun bindStorageRepository(storageImpl: StorageRepositoryImpl): StorageRepository

    @Binds
    @Singleton
    abstract fun bindLocalDataSource(dataSourceImpl: DataStoreLocalDataSource): LocalStorageDataSource
}