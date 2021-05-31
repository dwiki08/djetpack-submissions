package com.dice.djetmovie.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.dice.djetmovie.R
import com.dice.djetmovie.adapter.ViewPagerAdapter
import com.dice.djetmovie.data.Constants
import com.dice.djetmovie.databinding.ActivityMainBinding
import com.dice.djetmovie.ui.favorite.FavoritesActivity
import com.dice.djetmovie.ui.search.SearchActivity
import com.google.android.material.tabs.TabLayoutMediator
import org.jetbrains.anko.startActivity

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorites -> startActivity<FavoritesActivity>()
            R.id.menu_search -> startActivity<SearchActivity>()
        }
        return super.onOptionsItemSelected(item)
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

        binding.viewPager.apply {
            adapter = vp2Adapter
            offscreenPageLimit = 2
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titleList[position]
            binding.viewPager.setCurrentItem(tab.position, true)
        }.attach()
    }
}