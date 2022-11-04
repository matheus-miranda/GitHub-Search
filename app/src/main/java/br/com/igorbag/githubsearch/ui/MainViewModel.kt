package br.com.igorbag.githubsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.igorbag.githubsearch.domain.ResultStatus
import br.com.igorbag.githubsearch.domain.error.ErrorEntity
import br.com.igorbag.githubsearch.domain.model.UserRepo
import br.com.igorbag.githubsearch.domain.repository.StorageRepository
import br.com.igorbag.githubsearch.domain.repository.UserRepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: UserRepoRepository,
    private val storageRepository: StorageRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.EmptyList)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _savedUserName = MutableStateFlow("")
    val savedUserName: StateFlow<String> = _savedUserName

    init {
        retrieveSavedUserName()
    }

    fun search(username: String) {
        viewModelScope.launch {
            repository.fetchRepoList(username).collect { result ->
                when (result) {
                    ResultStatus.Loading -> _uiState.value = UiState.EmptyList
                    is ResultStatus.Error -> _uiState.value = UiState.Error(result.error)
                    is ResultStatus.Success -> _uiState.value = UiState.Success(result.data)
                }
            }
        }
    }

    fun saveUserName(username: String) {
        viewModelScope.launch {
            storageRepository.saveUser(username)
        }
    }

    private fun retrieveSavedUserName() {
        viewModelScope.launch {
            storageRepository.getUser().collect {
                _savedUserName.value = it
            }
        }
    }
}

sealed class UiState {
    object EmptyList : UiState()
    data class Success(val repoList: List<UserRepo> = emptyList()) : UiState()
    data class Error(val error: ErrorEntity) : UiState()
}
