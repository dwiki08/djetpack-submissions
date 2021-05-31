package com.dice.djetmovie.ui.favorite

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.dice.djetmovie.R
import com.dice.djetmovie.adapter.ViewPagerAdapter
import com.dice.djetmovie.data.Constants
import com.dice.djetmovie.databinding.ActivityFavoritesBinding
import com.google.android.material.tabs.TabLayoutMediator

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()
        setViewPager()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.favorites)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setViewPager() {
        val fragmentList = listOf(
            FavoriteFilmsFragment.newInstance(Constants.FILM_TYPE_MOVIE),
            FavoriteFilmsFragment.newInstance(Constants.FILM_TYPE_TV_SHOW)
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