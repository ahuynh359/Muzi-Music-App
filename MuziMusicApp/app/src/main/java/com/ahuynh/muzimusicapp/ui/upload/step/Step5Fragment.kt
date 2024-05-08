package com.ahuynh.muzimusicapp.ui.upload.step

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.databinding.FragmentStep5Binding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.upload.UploadViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Step5Fragment : BaseFragment<FragmentStep5Binding>(FragmentStep5Binding::inflate) {
    private val viewModel by viewModels<UploadViewModel>({requireActivity()})
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.edtLyrics.addTextChangedListener {
            if(it == null || it.toString().trim().isEmpty()){
                binding.tvLeaveEmpty.visibility = View.VISIBLE
            } else {
                binding.tvLeaveEmpty.visibility = View.GONE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.edtLyrics.setText(viewModel.lyrics)
    }

    override fun onStop() {
        super.onStop()
        viewModel.lyrics = binding.edtLyrics.text.toString().trim()
    }

}