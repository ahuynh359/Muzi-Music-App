package com.ahuynh.muzimusicapp.ui.component.admin.type.detail_manage_type

import android.content.ActivityNotFoundException
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.databinding.FragmentManageTypeDetailBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.type.ManageTypeViewModel
import com.ahuynh.muzimusicapp.utils.helper.FileHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class ManageTypeDetailFragment :
    BaseFragment<FragmentManageTypeDetailBinding>(
        FragmentManageTypeDetailBinding::inflate
    ) {

    companion object {
        const val TAG = "ManageTyperDetail"
    }

    private val viewModel by viewModels<ManageTypeViewModel>({ requireActivity() })
    private lateinit var currentType: Type
    private lateinit var file: File
    private var fileChooser: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val file = FileHelper.from(requireContext(), uri)!!
            file.let {
                viewModel.changeAvatar(currentType.id, it)
            }
        } else {
            Toast.makeText(requireContext(), "No file chosen", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentType = ManageTypeDetailFragmentArgs.fromBundle(requireArguments()).type


    }


    override fun onResume() {
        super.onResume()
        viewModel.getTypeById(currentType.id)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleUI()
        observe()

    }


    private fun observe() {

        viewModel.type.observe(viewLifecycleOwner) {
            it?.let {
                Glide
                    .with(binding.imvAvatar.context)
                    .load(it.avatar)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.imvAvatar)
                binding.tvType.text = it.id.toString()
                binding.edtType.setText(it.name)

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

        viewModel.updateTypeStatus.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    findNavController().popBackStack()
                }
                viewModel.mess?.let { mess ->
                    Toast.makeText(requireContext(), mess, Toast.LENGTH_LONG).show()
                }

            }
            viewModel.updateTypeStatus.postValue(null)
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
            val str = binding.edtType.text.toString().trim()
            if (str.isNotEmpty()) {
                viewModel.updateType(currentType.id, str)
            } else {
                Toast.makeText(requireContext(), "Not Leave empty", Toast.LENGTH_SHORT).show()
            }
        }


    }


}