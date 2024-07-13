package com.ahuynh.muzimusicapp.ui.component.admin.album.add_album

import android.content.ActivityNotFoundException
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.databinding.FragmentAddAlbumBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.album.ManageAlbumViewModel
import com.ahuynh.muzimusicapp.utils.helper.FileHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class AddAlbumFragment : BaseFragment<FragmentAddAlbumBinding>(FragmentAddAlbumBinding::inflate) {
    private val viewModel by viewModels<ManageAlbumViewModel>({ requireActivity() })
    private var isCreateOk = false
    private   var file : File ?= null
    private lateinit var fileChooser: ActivityResultLauncher<String>

    private val loginTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            val name = binding.edtAlbum.text.toString().trim()
            isCreateOk = name.isNotEmpty()
            if (isCreateOk) {
                binding.btnAdd.setBackgroundResource(R.drawable.btn_enable)
            } else
                binding.btnAdd.setBackgroundResource(R.drawable.btn_disable)
        }

    }

    companion object {
        const val TAG = "AddAlbumFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.album_1)
        val bitmap = (drawable as BitmapDrawable).bitmap
        file = saveBitmapToFile(bitmap, requireContext())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Khởi tạo fileChooser
        fileChooser = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                file = FileHelper.from(requireContext(), it)
                file?.let { selectedFile ->
                    Glide.with(binding.imvAvatar.context)
                        .load(selectedFile)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.imvAvatar)
                }
            }
        }

        // Thiết lập file mặc định và hình ảnh mặc định
        file = FileHelper.getDefaultImageFile(requireContext(), R.drawable.album_1)
        file?.let { defaultFile ->
            Glide.with(binding.imvAvatar.context)
                .load(defaultFile)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imvAvatar)
        }

        handleUI()
        observeData()

    }

    private fun saveBitmapToFile(bitmap: Bitmap, context: Context): File {
        val filesDir = context.filesDir
        val imageFile = File(filesDir, "default_Album")

        val outputStream = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        return imageFile
    }

    private fun observeData() {
        viewModel.createAlbumStatus.observe(viewLifecycleOwner) {
            it?.let {
                if(it){
                    findNavController().popBackStack()
                }
                viewModel.mess?.let { mess ->
                    Toast.makeText(requireContext(), mess, Toast.LENGTH_LONG).show()
                }

            }
            viewModel.createAlbumStatus.postValue(null)
        }


        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.btnAdd.isEnabled = !it
            if (it == true) {
                binding.pgLoading.show()
            } else
                binding.pgLoading.hide()
        }


    }

    private fun handleUI() {

        binding.edtAlbum.addTextChangedListener(loginTextWatcher)

        binding.btnAdd.setOnClickListener {
            if (isCreateOk) {

                viewModel.createAlbum(binding.edtAlbum.text.toString().trim(),file!!)


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


        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }


    }




}
