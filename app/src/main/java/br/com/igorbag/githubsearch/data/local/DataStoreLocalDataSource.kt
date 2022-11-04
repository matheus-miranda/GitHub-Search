package br.com.igorbag.githubsearch.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import br.com.igorbag.githubsearch.data.Constants
import br.com.igorbag.githubsearch.domain.repository.LocalStorageDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
) : LocalStorageDataSource {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = Constants.GITHUB_DATA_STORE_NAME
    )

    private val userNameKey = stringPreferencesKey(Constants.USER_NAME_KEY)

    override fun getUser(): Flow<String> {
        return context.dataStore.data.map { userStore ->
            userStore[userNameKey].orEmpty()
        }
    }

    override suspend fun saveUser(user: String) {
        context.dataStore.edit { userNameStore ->
            userNameStore[userNameKey] = user
        }
    }
}