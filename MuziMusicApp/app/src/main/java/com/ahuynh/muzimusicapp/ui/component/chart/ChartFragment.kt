package com.ahuynh.muzimusicapp.ui.component.chart

import android.os.Bundle
import android.view.View
import com.ahuynh.muzimusicapp.databinding.FragmentChartBinding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartFragment : BaseFragment<FragmentChartBinding>(FragmentChartBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}