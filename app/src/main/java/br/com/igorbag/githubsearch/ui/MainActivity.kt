package br.com.igorbag.githubsearch.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.igorbag.githubsearch.R
import br.com.igorbag.githubsearch.databinding.ActivityMainBinding
import br.com.igorbag.githubsearch.domain.error.ErrorEntity
import br.com.igorbag.githubsearch.domain.model.UserRepo
import br.com.igorbag.githubsearch.ui.adapter.RepositoryAdapter
import br.com.igorbag.githubsearch.ui.util.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModels()
    private val repoAdapter by lazy {
        RepositoryAdapter(onShareClick = ::shareRepositoryLink, onCardClick = ::openBrowser)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListeners()
        getAllReposByUserName()
        showUserName()
    }

    private fun setupListeners() {
        binding.btnConfirm.setOnClickListener {
            val userName = binding.etUserName.text.toString().trimEnd()
            if (userName.isNotBlank()) {
                saveUserNameToStorage(userName)
                viewModel.search(userName)
                hideKeyboard(it)
                binding.pbLoading.isVisible = true
            } else {
                displayOnSnackBar(getString(R.string.enter_username))
            }
        }
    }

    private fun saveUserNameToStorage(userName: String) {
        viewModel.saveUserName(userName)
    }

    private fun showUserName() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.savedUserName.collect { userName ->
                    binding.etUserName.setText(userName)
                }
            }
        }
    }

    private fun getAllReposByUserName() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::collect)
            }
        }
    }

    private fun collect(uiState: UiState) {
        when (uiState) {
            UiState.EmptyList -> { /* TODO(Add empty view) */ }
            is UiState.Error -> errorState(uiState)
            is UiState.Success -> setupAdapter(uiState.repoList)
        }
    }

    private fun errorState(uiState: UiState.Error) {
        val message = when (uiState.error) {
            ErrorEntity.Network -> R.string.network_error_message
            ErrorEntity.NotFound -> R.string.user_not_found_message
            ErrorEntity.ServiceUnavailable -> R.string.service_unavailable_message
            ErrorEntity.Unknown -> R.string.unknown_error_message
        }
        binding.pbLoading.isVisible = false
        displayOnSnackBar(getString(message))
    }

    private fun setupAdapter(list: List<UserRepo>) {
        binding.rvRepositoryList.apply {
            adapter = repoAdapter
            setHasFixedSize(true)
        }
        repoAdapter.submitList(list)
        binding.pbLoading.isVisible = false
    }

    private fun shareRepositoryLink(urlRepository: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, urlRepository)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun openBrowser(urlRepository: String) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(urlRepository)
            )
        )
    }

    private fun displayOnSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}