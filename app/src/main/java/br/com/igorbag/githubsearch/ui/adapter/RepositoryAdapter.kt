package br.com.igorbag.githubsearch.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import br.com.igorbag.githubsearch.domain.model.UserRepo

class RepositoryAdapter(
    private val onShareClick: (String) -> Unit,
    private val onCardClick: (String) -> Unit,
) : ListAdapter<UserRepo, RepositoryViewHolder>(diffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder.create(parent, onShareClick, onCardClick)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        getItem(position)?.let { userRepo -> holder.bind(userRepo) }
    }

    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<UserRepo>() {
            override fun areItemsTheSame(oldItem: UserRepo, newItem: UserRepo) = oldItem.name == newItem.name
            override fun areContentsTheSame(oldItem: UserRepo, newItem: UserRepo) = oldItem == newItem
        }
    }
}