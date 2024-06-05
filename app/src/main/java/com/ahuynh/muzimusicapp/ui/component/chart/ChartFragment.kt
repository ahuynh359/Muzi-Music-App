package com.ahuynh.muzimusicapp.ui.component.chart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.databinding.FragmentChartBinding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.song.SongViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartFragment : BaseFragment<FragmentChartBinding>(FragmentChartBinding::inflate){

    companion object {
        const val TAG = "ChartFragment"
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }




}