package com.ahuynh.muzimusicapp.ui.component.admin.song.add_song.step

import android.content.ActivityNotFoundException
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.databinding.FragmentStep1Binding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.song.add_song.UploadViewModel
import com.ahuynh.muzimusicapp.utils.helper.FileHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class Step1Fragment : BaseFragment<FragmentStep1Binding>(FragmentStep1Binding::inflate) {
    private val viewModel by viewModels<UploadViewModel>({ requireActivity() })
    private lateinit var file: File
    private var fileChooser: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            file = FileHelper.from(requireContext(), uri)!!
            file.let {
                Glide
                    .with(binding.imvAvatar.context)
                    .load(it)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.imvAvatar)
                viewModel.avatar = it
            }
        } else {
            Toast.makeText(requireContext(), "No file chosen", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.song)
        val bitmap = (drawable as BitmapDrawable).bitmap
        file = saveBitmapToFile(bitmap, requireContext())
        viewModel.avatar = saveBitmapToFile(bitmap, requireContext())
    }

    private fun saveBitmapToFile(bitmap: Bitmap, context: Context): File {
        val filesDir = context.filesDir
        val imageFile = File(filesDir, "default_type")

        val outputStream = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        return imageFile
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.edtSongName.addTextChangedListener {
            if (it == null || it.toString().trim().isEmpty()) {
                binding.edtSongName.error = "Name cannot be empty"
            } else {
                binding.edtSongName.error = null
                viewModel.name = binding.edtSongName.text.toString().trim()
            }
        }
        binding.btnEdit.setOnClickListener {
            try {
                fileChooser.launch("image/*")
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(
                    requireContext(),
                    "Vui lòng cài đặt File Manager",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.edtSongName.setText(viewModel.name)
        binding.tvName.text = viewModel.avatar?.name
    }

    override fun onStop() {
        super.onStop()
        viewModel.name = binding.edtSongName.text.toString().trim()
        binding.tvName.text = viewModel.avatar?.name
    }
}
