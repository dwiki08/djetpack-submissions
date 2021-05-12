package com.dice.djetmovie.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val listFragment = mutableListOf<Fragment>()

    override fun getItemCount(): Int = listFragment.size

    override fun createFragment(position: Int): Fragment = listFragment[position]

    fun addFragment(fragment: Fragment) {
        listFragment.add(fragment)
    }
}