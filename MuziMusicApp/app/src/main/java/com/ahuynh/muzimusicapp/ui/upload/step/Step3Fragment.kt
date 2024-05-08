package com.ahuynh.muzimusicapp.ui.upload.step

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.databinding.FragmentStep3Binding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.upload.UploadViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Step3Fragment : BaseFragment<FragmentStep3Binding>(FragmentStep3Binding::inflate) {
    private val viewModel by viewModels<UploadViewModel>({requireActivity()})
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.edtSinger.addTextChangedListener {
            if(it == null || it.toString().trim().isEmpty()){
                binding.tvLeaveEmpty.visibility = View.VISIBLE
            } else {
                binding.tvLeaveEmpty.visibility = View.GONE
                viewModel.singerName = binding.edtSinger.text.toString().trim()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.edtSinger.setText(viewModel.singerName)
    }

    override fun onStop() {
        super.onStop()
        viewModel.singerName = binding.edtSinger.text.toString().trim()
    }
}