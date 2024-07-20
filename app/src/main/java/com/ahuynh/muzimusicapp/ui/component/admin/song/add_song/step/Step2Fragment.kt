package com.ahuynh.muzimusicapp.ui.component.admin.song.add_song.step

import android.content.ActivityNotFoundException
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.databinding.FragmentStep2Binding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.song.add_song.UploadViewModel
import com.ahuynh.muzimusicapp.utils.helper.FileHelper
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class Step2Fragment : BaseFragment<FragmentStep2Binding>(FragmentStep2Binding::inflate) {
    private val viewModel by viewModels<UploadViewModel>({ requireActivity() })
    private var file: File? = null
    private var fileChooser: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { ur ->
            file = FileHelper.from(requireContext(), uri)
            file?.let {
                viewModel.file = it
                binding.edtFileMusic.text = it.name
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()

    }

    private fun handleUI() {
        binding.edtFileMusic.setOnClickListener {
            try {
                fileChooser.launch("audio/mpeg")
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "Install file manager", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.edtLyrics.setText(viewModel.lyrics)
        viewModel.file?.let {
            binding.edtFileMusic.text = it.name
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.lyrics = binding.edtLyrics.text.toString().trim()
        viewModel.file = file
    }


}