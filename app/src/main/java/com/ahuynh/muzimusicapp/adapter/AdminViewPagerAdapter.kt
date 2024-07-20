package com.ahuynh.muzimusicapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ahuynh.muzimusicapp.ui.component.admin.AdminActivity

class AdminViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val navGraphIds: List<Int>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = navGraphIds.size

    override fun createFragment(position: Int): Fragment {
        return NavHostFragment.create(navGraphIds[position])
    }
}