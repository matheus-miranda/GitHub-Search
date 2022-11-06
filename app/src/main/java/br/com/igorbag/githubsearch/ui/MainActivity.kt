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
import androidx.paging.LoadState
import br.com.igorbag.githubsearch.R
import br.com.igorbag.githubsearch.databinding.ActivityMainBinding
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
        bindRecyclerView()
        bindListeners()
        getAllReposByUserName()
        showLoadingIndicators()
        showUserName()
    }

    private fun bindRecyclerView() {
        binding.rvRepositoryList.apply {
            adapter = repoAdapter
            setHasFixedSize(true)
        }
    }

    private fun bindListeners() {
        binding.btnConfirm.setOnClickListener {
            val userName = binding.etUserName.text.toString().trimEnd()
            if (userName.isNotBlank()) {
                binding.pbLoading.isVisible = true
                saveUserNameToStorage(userName)
                viewModel.search(userName)
                hideKeyboard(it)
            } else {
                binding.etUserName.error = getString(R.string.enter_username)
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
        viewModel.repoListData.observe(this) {
            val userName = binding.etUserName.text.toString()
            if (userName.isNotBlank()) {
                repoAdapter.submitData(this.lifecycle, it)
                binding.pbLoading.isVisible = false
            }
        }
    }

    private fun showLoadingIndicators() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                repoAdapter.loadStateFlow.collect { combinedLoadStates ->
                    val userName = binding.etUserName.text.toString()
                    if (userName.isNotBlank()) {
                        binding.pbPrepend.isVisible = combinedLoadStates.source.prepend is LoadState.Loading
                        binding.pbAppend.isVisible = combinedLoadStates.source.append is LoadState.Loading
                        binding.pbLoading.isVisible = combinedLoadStates.source.refresh is LoadState.Loading
                        if (combinedLoadStates.refresh is LoadState.Error)
                            displayOnSnackBar(getString(R.string.generic_error_message))
                    }
                }
            }
        }
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