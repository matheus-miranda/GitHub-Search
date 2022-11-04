package br.com.igorbag.githubsearch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.igorbag.githubsearch.databinding.RepositoryItemBinding
import br.com.igorbag.githubsearch.domain.model.UserRepo

class RepositoryViewHolder(
    binding: RepositoryItemBinding,
    private val onShareClick: (String) -> Unit,
    private val onCardClick: (String) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private val cvRepoCard = binding.cvCard
    private val ivFavorite = binding.ivFavorite
    private val tvRepoName = binding.tvRepoName

    fun bind(userRepo: UserRepo) {
        tvRepoName.text = userRepo.name

        ivFavorite.setOnClickListener {
            onShareClick(userRepo.htmlUrl)
        }
        cvRepoCard.setOnClickListener {
            onCardClick(userRepo.htmlUrl)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onShareClick: (String) -> Unit,
            onCardClick: (String) -> Unit,
        ): RepositoryViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = RepositoryItemBinding.inflate(inflater, parent, false)
            return RepositoryViewHolder(itemBinding, onShareClick, onCardClick)
        }
    }
}