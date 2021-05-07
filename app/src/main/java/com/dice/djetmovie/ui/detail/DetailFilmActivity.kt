package com.dice.djetmovie.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dice.djetmovie.R
import com.dice.djetmovie.databinding.ActivityDetailFilmBinding
import com.dice.djetmovie.repository.model.Film
import com.dice.djetmovie.utils.Utils
import com.google.android.material.appbar.AppBarLayout
import org.jetbrains.anko.share

class DetailFilmActivity : AppCompatActivity() {
    companion object {
        const val EXTRAS_FILM = "extras_film"
    }

    private lateinit var binding: ActivityDetailFilmBinding
    private var film: Film? = null
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailFilmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            film = it.getParcelable(EXTRAS_FILM)
        }
        setToolbar()
        setView()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun setView() {
        film?.let { film ->
            with(binding) {
                Utils.setImage(
                    applicationContext,
                    binding.imgPoster,
                    ContextCompat.getDrawable(applicationContext, film.posterRes)
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
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.menu_share -> share(getString(R.string.share_template, film?.title))
        }
        return super.onOptionsItemSelected(item)
    }
}