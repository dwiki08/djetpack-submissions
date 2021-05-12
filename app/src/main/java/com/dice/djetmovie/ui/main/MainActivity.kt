package com.dice.djetmovie.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dice.djetmovie.R
import com.dice.djetmovie.adapter.ViewPagerAdapter
import com.dice.djetmovie.data.Constants
import com.dice.djetmovie.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

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

        val vp2Adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        for (fragment in fragmentList) {
            vp2Adapter.addFragment(fragment)
        }

        binding.viewPager.adapter = vp2Adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titleList[position]
            binding.viewPager.setCurrentItem(tab.position, true)
        }.attach()
    }
}