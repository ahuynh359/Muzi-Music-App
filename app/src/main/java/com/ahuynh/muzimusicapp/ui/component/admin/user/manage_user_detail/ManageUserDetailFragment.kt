package com.ahuynh.muzimusicapp.ui.component.admin.user.manage_user_detail


import android.content.ActivityNotFoundException
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.data.model.request.UpdateUserRequest
import com.ahuynh.muzimusicapp.databinding.FragmentManageUserDetailBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.user.ManageUserViewModel
import com.ahuynh.muzimusicapp.utils.Utils
import com.ahuynh.muzimusicapp.utils.helper.FileHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageUserDetailFragment : BaseFragment<FragmentManageUserDetailBinding>(
    FragmentManageUserDetailBinding::inflate
) {

    companion object {
        const val TAG = "ManageUserDetail"
    }

    private var isUpdateOk = false
    private val viewModel by viewModels<ManageUserViewModel>({ requireActivity() })
    private lateinit var currentUser: User
    private var fileChooser: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val file = FileHelper.from(requireContext(), uri)!!
            file.let {
                viewModel.changeAvatar(currentUser.id,it)
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
            val email = binding.edtEmail.text.toString().trim()
            val username = binding.edtUserName.text.toString().trim()
            val isEmail = Utils.isValidEmail(email)
            isUpdateOk = email.isNotEmpty() && username.isNotEmpty() && isEmail
            if (!isEmail) {
                binding.edtEmail.error = "Email is invalid"
            } else {
                binding.edtEmail.error = null

            }
            if (isUpdateOk) {
                binding.btnDone.setBackgroundResource(R.drawable.btn_enable)
            } else
                binding.btnDone.setBackgroundResource(R.drawable.btn_disable)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentUser = ManageUserDetailFragmentArgs.fromBundle(requireArguments()).user

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleUI()
        observe()

    }


    private fun observe() {

        currentUser?.let {
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
                binding.btnLock.setBackgroundResource(R.drawable.bg_btn_lock)

            } else {
                binding.btnLock.setText(R.string.unlocked)
                binding.btnLock.setBackgroundResource(R.drawable.bg_btn_unlock)
            }
            binding.tvCreatedAt.text = it.createdAt
            binding.tvUpdatedAt.text = it.updatedAt

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

        viewModel.updateUserStatus.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    findNavController().popBackStack()
                }
                viewModel.mess?.let { mess ->
                    Toast.makeText(requireContext(), mess, Toast.LENGTH_SHORT).show()
                }
            }

            viewModel.updateUserStatus.postValue(null)
        }


    }


    private fun handleUI() {
        binding.edtEmail.addTextChangedListener(loginTextWatcher)
        binding.edtUserName.addTextChangedListener(loginTextWatcher)

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
            if (isUpdateOk) {
                val updateUserRequest = UpdateUserRequest(
                    currentUser.id,
                    binding.edtEmail.text.toString().trim(),
                    binding.edtUserName.text.toString().trim()
                )
                viewModel.updateUser(
                    updateUserRequest
                )
            }
        }


    }


}