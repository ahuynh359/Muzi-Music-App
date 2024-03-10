package com.ahuynh.muzimusicapp.ui.component.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.databinding.FragmentHomeBinding
import com.ahuynh.muzimusicapp.ui.component.album.AlbumFragment
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
