package com.dice.djetmovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dice.djetmovie.R
import com.dice.djetmovie.databinding.ItemFilmStateBinding

class FilmLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<FilmLoadStateAdapter.FilmLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: FilmLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): FilmLoadStateViewHolder {
        return FilmLoadStateViewHolder.create(parent, retry)
    }

    class FilmLoadStateViewHolder(
        private val binding: ItemFilmStateBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible = loadState is LoadState.Error
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): FilmLoadStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_film_state, parent, false)
                val binding = ItemFilmStateBinding.bind(view)
                return FilmLoadStateViewHolder(binding, retry)
            }
        }
    }
}
