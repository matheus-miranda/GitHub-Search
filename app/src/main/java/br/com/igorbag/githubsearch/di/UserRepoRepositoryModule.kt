package br.com.igorbag.githubsearch.di

import br.com.igorbag.githubsearch.data.error.ErrorHandlerImpl
import br.com.igorbag.githubsearch.data.remote.RetrofitUserRepoDataSource
import br.com.igorbag.githubsearch.data.repository.UserRepoRepositoryImpl
import br.com.igorbag.githubsearch.domain.error.ErrorHandler
import br.com.igorbag.githubsearch.domain.repository.UserRepoRemoteDataSource
import br.com.igorbag.githubsearch.domain.repository.UserRepoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserRepoRepositoryModule {

    @Binds
    abstract fun bindUserRepoRepository(repositoryImpl: UserRepoRepositoryImpl): UserRepoRepository

    @Binds
    abstract fun bindRemoteDataSource(dataSource: RetrofitUserRepoDataSource): UserRepoRemoteDataSource

    @Binds
    abstract fun bindErrorHandler(errorHandlerImpl: ErrorHandlerImpl): ErrorHandler
}