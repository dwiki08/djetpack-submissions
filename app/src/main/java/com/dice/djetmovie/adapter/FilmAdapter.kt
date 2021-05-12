package com.dice.djetmovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dice.djetmovie.data.model.Film
import com.dice.djetmovie.databinding.ItemFilmBinding
import com.dice.djetmovie.utils.Utils

class FilmAdapter : RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {
    private val filmList = ArrayList<Film>()

    companion object {
        internal var clickListener: ClickListener? = null
    }

    interface ClickListener {
        fun onItemClick(data: Film)
    }

    fun setOnItemClickListener(clickListener: ClickListener) {
        FilmAdapter.clickListener = clickListener
    }

    fun setData(items: List<Film>) {
        filmList.clear()
        filmList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(binding)
    }

    override fun getItemCount(): Int = filmList.size

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(filmList[position])
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
                    clickListener?.onItemClick(film)
                }
            }
        }
    }
}