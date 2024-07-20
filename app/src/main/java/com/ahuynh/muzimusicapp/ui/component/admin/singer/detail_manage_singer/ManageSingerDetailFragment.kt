package com.ahuynh.muzimusicapp.ui.component.admin.singer.detail_manage_singer

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.databinding.FragmentManageSingerDetailBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.singer.ManageSingerViewModel
import com.ahuynh.muzimusicapp.utils.helper.FileHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ManageSingerDetailFragment :
    BaseFragment<FragmentManageSingerDetailBinding>(
        FragmentManageSingerDetailBinding::inflate
    ) {

    companion object {
        const val TAG = "ManageSingerDetailFragment"
    }

    private val viewModel by viewModels<ManageSingerViewModel>({ requireActivity() })
    private lateinit var currentSinger: Singer
    private lateinit var file: File
    private var fileChooser: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val file = FileHelper.from(requireContext(), uri)!!
            file.let {
                viewModel.changeAvatar(currentSinger.id, it)
            }
        } else {
            Toast.makeText(requireContext(), "No file chosen", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentSinger = ManageSingerDetailFragmentArgs.fromBundle(requireArguments()).singer


    }


    override fun onResume() {
        super.onResume()
        viewModel.getSingerById(currentSinger.id)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleUI()
        observe()

    }


    private fun observe() {

        viewModel.singer.observe(viewLifecycleOwner) {
            it?.let {
                Glide
                    .with(binding.imvAvatar.context)
                    .load(it.avatar)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.imvAvatar)
                binding.tvSingerName.text = it.id.toString()
                binding.edtSinger.setText(it.name)

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

        viewModel.updateSingerStatus.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    findNavController().popBackStack()
                }
                viewModel.mess?.let { mess ->
                    Toast.makeText(requireContext(), mess, Toast.LENGTH_LONG).show()
                }

            }
            viewModel.updateSingerStatus.postValue(null)
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
            val str = binding.edtSinger.text.toString().trim()
            if (str.isNotEmpty()) {
                viewModel.updateSinger(currentSinger.id, str)
            } else {
                Toast.makeText(requireContext(), "Not Leave empty", Toast.LENGTH_SHORT).show()
            }
        }


    }


}