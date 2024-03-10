package com.ahuynh.muzimusicapp.ui.component.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.databinding.FragmentHomeBinding
import com.ahuynh.muzimusicapp.databinding.FragmentSearchBinding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
