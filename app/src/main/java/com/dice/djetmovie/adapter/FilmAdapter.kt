package com.dice.djetmovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dice.djetmovie.data.model.Film
import com.dice.djetmovie.databinding.ItemFilmBinding
import com.dice.djetmovie.ui.detail.DetailFilmActivity
import com.dice.djetmovie.utils.Utils
import org.jetbrains.anko.startActivity

class FilmAdapter : PagingDataAdapter<Film, FilmAdapter.FilmViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class FilmViewHolder(private val binding: ItemFilmBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film) {
            with(binding) {
                Utils.setPosterImage(
                        itemView.context,
                        imgPoster,
                        film.posterPath
                )
                tvTitle.text = film.title
                tvDateRelease.text = film.releaseDate

                itemView.setOnClickListener {
                    binding.root.context.startActivity<DetailFilmActivity>(DetailFilmActivity.EXTRAS_FILM to film)
                }
            }
        }
    }

    companion object {
        private val ITEM_COMPARATOR: DiffUtil.ItemCallback<Film> =
                object : DiffUtil.ItemCallback<Film>() {
                    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
                        return oldItem == newItem
                    }

                    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
                        return oldItem == newItem
                    }
                }
    }
}