package com.dice.djetmovie.ui.search

import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.dice.djetmovie.R
import com.dice.djetmovie.adapter.ViewPagerAdapter
import com.dice.djetmovie.data.Constants
import com.dice.djetmovie.databinding.ActivitySearchBinding
import com.google.android.material.tabs.TabLayoutMediator
import org.greenrobot.eventbus.EventBus

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()
        setViewPager()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)

        binding.edtQuery.apply {
            setOnEditorActionListener { v, actionId, _ ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        searchFilms(v.text.toString())
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun searchFilms(query: String?) {
        if (!query.isNullOrEmpty()) EventBus.getDefault().post(SearchData(query))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setViewPager() {
        val fragmentList = listOf(
            SearchFragment.newInstance(Constants.FILM_TYPE_MOVIE),
            SearchFragment.newInstance(Constants.FILM_TYPE_TV_SHOW)
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