package com.dice.djetmovie.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dice.djetmovie.R
import com.dice.djetmovie.adapter.ViewPagerAdapter
import com.dice.djetmovie.databinding.ActivityMainBinding
import com.dice.djetmovie.repository.Constants

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()
        setViewPager()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun setViewPager() {
        val fragmentList = listOf(
            FilmsFragment.newInstance(Constants.FILM_TYPE_MOVIE),
            FilmsFragment.newInstance(Constants.FILM_TYPE_TV_SHOW)
        )
        val titleList = listOf(
            getString(R.string.movies),
            getString(R.string.tv_shows)
        )

        binding.viewPager.apply {
            adapter = ViewPagerAdapter(supportFragmentManager, fragmentList, titleList)
            binding.tabLayout.setupWithViewPager(this)
        }
    }
}