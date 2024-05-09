package com.ahuynh.muzimusicapp.ui.component.upload.step

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.databinding.FragmentStep2Binding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.upload.UploadViewModel
import com.ahuynh.muzimusicapp.utils.FileUtils
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class Step2Fragment : BaseFragment<FragmentStep2Binding>(FragmentStep2Binding::inflate) {
    private val viewModel by viewModels<UploadViewModel>({ requireActivity() })
    private var fileChooser: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { ur ->
            val file: File? = FileUtils.from(requireContext(), uri)
            file?.let {
                viewModel.songFile = it
                showSongFileInfo(it)
            }
        }
    }

    private fun showSongFileInfo(it: File) {
        binding.btnChooseMusic.text = it.name
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()

    }

    private fun handleUI() {
        binding.btnChooseMusic.setOnClickListener {
            try {
                fileChooser.launch("audio/mpeg")
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "Install file manager", Toast.LENGTH_SHORT).show()
            }
        }



    }

    override fun onStart() {
        super.onStart()
        viewModel.songFile?.let {
            showSongFileInfo(it)
        }


    }

    override fun onStop() {
        super.onStop()

    }



}