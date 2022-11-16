package br.com.igorbag.githubsearch.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
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

    init {
        retrieveSavedUserName()
    }

    private val _savedUserName = MutableStateFlow("")
    val savedUserName: StateFlow<String> = _savedUserName.asStateFlow()

    private val _currentUserName = MutableLiveData("")
    val repoListData = _currentUserName.switchMap { repository.fetchRepoList(it).cachedIn(viewModelScope) }

    fun search(username: String) {
        _currentUserName.value = username
    }

    fun saveUserName(username: String) {
        viewModelScope.launch {
            storageRepository.saveUser(username)
        }
    }

    fun retrieveSavedUserName() {
        viewModelScope.launch {
            storageRepository.getUser().collect {
                _savedUserName.value = it
            }
        }
    }
}