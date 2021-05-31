package com.dice.djetmovie.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dice.djetmovie.R
import com.dice.djetmovie.data.model.Film
import com.dice.djetmovie.databinding.ActivityDetailFilmBinding
import com.dice.djetmovie.utils.Utils
import com.google.android.material.appbar.AppBarLayout
import org.jetbrains.anko.share
import org.koin.android.viewmodel.ext.android.viewModel

class DetailFilmActivity : AppCompatActivity() {
    companion object {
        const val EXTRAS_FILM = "extras_film"
    }

    private lateinit var binding: ActivityDetailFilmBinding
    private var film: Film? = null
    private var isFavorite = false
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailFilmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            film = it.getParcelable(EXTRAS_FILM)
        }

        setToolbar()
        setView()
        observe()
    }

    private fun observe() {
        viewModel.isFavorite.observe(this, {
            val menu = binding.toolbar.menu.findItem(R.id.menu_add_favorite)
            menu.icon = if (it) ContextCompat.getDrawable(this, R.drawable.ic_favorite_accent)
            else ContextCompat.getDrawable(this, R.drawable.ic_favorite_border)
            isFavorite = it
        })
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun setView() {
        film?.let { film ->
            with(binding) {
                Utils.setPosterImage(
                    applicationContext,
                    binding.imgPoster,
                    film.posterPath
                )
                Utils.setBackdropImage(
                        applicationContext,
                        binding.imgBackdrop,
                        film.backdropPath
                )
                tvTitle.text = film.title
                tvDateRelease.text = film.releaseDate
                tvOverview.text = film.overview

                appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, offset ->
                    if (offset < -500) {
                        collapsingToolbar.title = film.title
                    } else {
                        collapsingToolbar.title = ""
                    }
                })
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_film, menu)
        film?.let { viewModel.checkIsFavorite(it) }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.menu_share -> share(getString(R.string.share_template, film?.title))
            R.id.menu_add_favorite -> {
                film?.let {
                    if (isFavorite) {
                        when (it.type) {
                            Film.TYPE.MOVIE -> viewModel.removeMovieFavorite(it)
                            Film.TYPE.TV_SHOW -> viewModel.removeTvShowFavorite(it)
                        }
                    } else {
                        when (it.type) {
                            Film.TYPE.MOVIE -> viewModel.addMovieFavorite(it)
                            Film.TYPE.TV_SHOW -> viewModel.addTvShowFavorite(it)
                        }
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}