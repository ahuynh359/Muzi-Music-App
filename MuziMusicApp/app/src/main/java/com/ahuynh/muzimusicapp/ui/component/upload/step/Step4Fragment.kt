package com.ahuynh.muzimusicapp.ui.component.upload.step

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.databinding.FragmentStep4Binding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.upload.UploadViewModel
import com.ahuynh.muzimusicapp.utils.FileUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class Step4Fragment : BaseFragment<FragmentStep4Binding>(FragmentStep4Binding::inflate) {
    private val viewModel by viewModels<UploadViewModel>({requireActivity()})
    private var imageChooser: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { ur ->
            val file: File? = FileUtils.from(requireContext(), uri)
            file?.let {
                viewModel.imageFile = it
                showImageFileInfo(it)
            }
        }
    }

    private fun showImageFileInfo(it: File) {
        binding.btnChooseImage.text = it.name
        Glide
            .with(binding.imv.context)
            .load(it)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imv)
        binding.imv.visibility = View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
    }

    private fun handleUI() {
        binding.btnChooseImage.setOnClickListener {
            try{
                imageChooser.launch("image/*")
            }catch (e : ActivityNotFoundException){
                Toast.makeText(context,"Install File Manager", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.imv.visibility = View.INVISIBLE
        viewModel.imageFile?.let {
            showImageFileInfo(it)
        }


    }

    override fun onStop() {
        super.onStop()

    }
}