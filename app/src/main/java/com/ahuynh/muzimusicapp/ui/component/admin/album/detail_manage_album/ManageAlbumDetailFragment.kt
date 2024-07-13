package com.ahuynh.muzimusicapp.ui.component.admin.album.detail_manage_album

import android.content.ActivityNotFoundException
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.databinding.FragmentManageAlbumDetailBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.album.ManageAlbumViewModel
import com.ahuynh.muzimusicapp.utils.helper.FileHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class ManageAlbumDetailFragment :
    BaseFragment<FragmentManageAlbumDetailBinding>(
        FragmentManageAlbumDetailBinding::inflate
    ) {

    companion object {
        const val TAG = "ManageAlbumDetailFragment"
    }

    private val viewModel by viewModels<ManageAlbumViewModel>({ requireActivity() })
    private lateinit var currentAlbum: Album
    private var file: File? = null
    private var fileChooser: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            file = FileHelper.from(requireContext(), it)
            file?.let { selectedFile ->
                viewModel.updateAvatar(currentAlbum.id, selectedFile)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentAlbum = ManageAlbumDetailFragmentArgs.fromBundle(requireArguments()).album
        viewModel.getAlbumById(currentAlbum.id)
    }

    override fun onResume() {
        super.onResume()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observe()

    }


    private fun observe() {

        viewModel.album.observe(viewLifecycleOwner) {
            it?.let {
                Glide
                    .with(binding.imvAvatar.context)
                    .load(it.avatar)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.imvAvatar)

                binding.edtAlbum.setText(it.name)

                binding.tvCreatedAt.text = it.createdAt
                binding.tvUpdatedAt.text = it.updatedAt


            }

        }

        viewModel.avatar.observe(viewLifecycleOwner) {

            Glide
                .with(binding.imvAvatar.context)
                .load(it)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imvAvatar);


        }

        viewModel.updateAlbumStatus.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    findNavController().popBackStack()
                }
                viewModel.mess?.let { mess ->
                    Toast.makeText(requireContext(), mess, Toast.LENGTH_LONG).show()
                }

            }
            viewModel.updateAlbumStatus.postValue(null)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.btnEdit.visibility = View.INVISIBLE
                binding.imvAvatar.visibility = View.INVISIBLE
                binding.pgLoadingAvatar.show()
            } else {
                binding.btnEdit.visibility = View.VISIBLE
                binding.imvAvatar.visibility = View.VISIBLE
                binding.pgLoadingAvatar.hide()
            }
        }


    }


    private fun handleUI() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
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

        binding.btnDone.setOnClickListener {
            val str = binding.edtAlbum.text.toString().trim()
            if (str.isNotEmpty()) {
                viewModel.updateAlbum(currentAlbum.id, str)
            } else {
                Toast.makeText(requireContext(), "Not Leave empty", Toast.LENGTH_SHORT).show()
            }
        }


    }


}