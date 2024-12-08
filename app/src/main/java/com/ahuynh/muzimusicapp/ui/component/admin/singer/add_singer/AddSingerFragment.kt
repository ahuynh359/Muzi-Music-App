package com.ahuynh.muzimusicapp.ui.component.admin.singer.add_singer

import android.content.ActivityNotFoundException
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.databinding.FragmentAddSingerBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.singer.ManageSingerViewModel
import com.ahuynh.muzimusicapp.utils.helper.FileHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class AddSingerFragment : BaseFragment<FragmentAddSingerBinding>(FragmentAddSingerBinding::inflate) {
    private val viewModel by viewModels<ManageSingerViewModel>({ requireActivity() })
    private var isCreateOk = false
    private  lateinit var file :  File
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
            }
        } else {
            Toast.makeText(requireContext(), "No file chosen", Toast.LENGTH_SHORT).show()
        }
    }

    private val loginTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            val name = binding.edtSinger.text.toString().trim()
            isCreateOk = name.isNotEmpty()
            if (isCreateOk) {
                binding.btnAdd.setBackgroundResource(R.drawable.btn_enable)
            } else
                binding.btnAdd.setBackgroundResource(R.drawable.btn_disable)
        }

    }

    companion object {
        const val TAG = "AddSingerFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.type_1)
        val bitmap = (drawable as BitmapDrawable).bitmap
        file = saveBitmapToFile(bitmap, requireContext())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
        observeData()

    }

    private fun saveBitmapToFile(bitmap: Bitmap, context: Context): File {
        val filesDir = context.filesDir
        val imageFile = File(filesDir, "default_Singer")

        val outputStream = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        return imageFile
    }

    private fun observeData() {
        viewModel.createSingerStatus.observe(viewLifecycleOwner) {
            it?.let {
                if(it){
                    findNavController().popBackStack()
                }
                viewModel.mess?.let { mess ->
                    Toast.makeText(requireContext(), mess, Toast.LENGTH_LONG).show()
                }

            }
            viewModel.createSingerStatus.postValue(null)
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

        binding.edtSinger.addTextChangedListener(loginTextWatcher)

        binding.btnAdd.setOnClickListener {
            if (isCreateOk) {

                viewModel.createSinger(binding.edtSinger.text.toString().trim(),binding.edtDescription.text.toString(),file)


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
