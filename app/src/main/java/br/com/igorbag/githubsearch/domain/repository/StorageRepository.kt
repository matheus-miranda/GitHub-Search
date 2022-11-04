package br.com.igorbag.githubsearch.domain.repository

import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    fun getUser(): Flow<String>
    suspend fun saveUser(user: String)
}