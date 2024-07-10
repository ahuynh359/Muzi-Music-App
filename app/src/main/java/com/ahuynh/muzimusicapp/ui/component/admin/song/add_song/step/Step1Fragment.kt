package com.ahuynh.muzimusicapp.ui.component.admin.song.add_song.step

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.databinding.FragmentStep1Binding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Step1Fragment : BaseFragment<FragmentStep1Binding>(FragmentStep1Binding::inflate) {
//    private val  viewModel by viewModels<UploadViewModel>({requireActivity()})
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.edtSongName.addTextChangedListener {
//            if(it == null || it.toString().trim().isEmpty()){
//                binding.tvLeaveEmpty.visibility = View.VISIBLE
//            } else {
//                binding.tvLeaveEmpty.visibility = View.GONE
//                viewModel.songName = binding.edtSongName.text.toString().trim()
//            }
//        }
//    }
//
//    override fun onStart() {
//        super.onStart()
//        binding.edtSongName.setText(viewModel.songName)
//    }
//
//    override fun onStop() {
//        super.onStop()
//        viewModel.songName = binding.edtSongName.text.toString().trim()
//    }
}