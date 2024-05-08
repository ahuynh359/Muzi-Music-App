package com.ahuynh.muzimusicapp.ui.upload.step

import android.os.Bundle
import android.view.View
import com.ahuynh.muzimusicapp.databinding.FragmentStep1Binding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Step1Fragment : BaseFragment<FragmentStep1Binding>(FragmentStep1Binding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}