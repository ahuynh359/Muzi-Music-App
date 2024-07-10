package com.ahuynh.muzimusicapp.ui.component.admin.user.detail_manage_user

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.databinding.FragmentManageUserDetailBinding
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.BaseDialogBottomSheetFragment
import com.ahuynh.muzimusicapp.ui.component.admin.user.ManageUserViewModel
import com.ahuynh.muzimusicapp.utils.helper.FileHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ManageUserDetailFragment :
    BaseDialogBottomSheetFragment() {

    companion object {
        const val TAG = "ManageUserDetail"
    }

    private val viewModel by viewModels<ManageUserViewModel>({ requireActivity() })
    private lateinit var currentUser: User
    private lateinit var binding: FragmentManageUserDetailBinding
    private var fileChooser: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        val file: File? = FileHelper.from(requireContext(), uri!!)
        file?.let {
            viewModel.changeAvatar(
                file
            )

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentUser = ManageUserDetailFragmentArgs.fromBundle(requireArguments()).user
        viewModel.getUserById(currentUser.id)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManageUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleUI()
        observe()

    }


    private fun observe() {

        viewModel.user.observe(viewLifecycleOwner) {
            it?.let {
                Glide
                    .with(binding.imvAvatar.context)
                    .load(it.avatar)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.imvAvatar)

                binding.edtEmail.setText(it.email)
                binding.edtUserName.setText(it.username)
                if (it.locked) {
                    binding.btnLock.setText(R.string.locked)
                } else
                    binding.btnLock.setText(R.string.unlocked)
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
            dismiss()
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


}