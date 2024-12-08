package com.ahuynh.muzimusicapp.ui.component.user.changpassword

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.databinding.FragmentChangePasswordBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment :
    BaseFragment<FragmentChangePasswordBinding>(FragmentChangePasswordBinding::inflate) {

    private var changePasswordEnable = false
    private val viewModel by viewModels<ChangePasswordViewModel>()
    private val changePasswordTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        override fun afterTextChanged(p0: Editable?) {
            validateInputs()
        }

    }

    private fun validateInputs() {
        val oldPasswordInput = binding.edtOldPassword.text.toString().trim()
        val passwordInput = binding.edtPassword.text.toString().trim()
        val confirmPasswordInput = binding.edtConfirmPassword.text.toString().trim()
        changePasswordEnable =
            oldPasswordInput.isNotEmpty() && passwordInput.isNotEmpty() && confirmPasswordInput.isNotEmpty() && passwordInput == confirmPasswordInput
        binding.btnChange.isEnabled = changePasswordEnable
        if (changePasswordEnable) {
            binding.btnChange.setBackgroundResource(R.drawable.btn_enable)
        } else
            binding.btnChange.setBackgroundResource(R.drawable.btn_disable)

        binding.edtOldPassword.error = when {
            oldPasswordInput.isEmpty() -> getString(R.string.can_not_be_empty)
            else -> null
        }
        binding.edtPassword.error = when {
            passwordInput.isEmpty() -> getString(R.string.can_not_be_empty)
            else -> null
        }
        binding.edtConfirmPassword.error = when {
            confirmPasswordInput.isEmpty() -> getString(R.string.can_not_be_empty)
            confirmPasswordInput != passwordInput -> getString(R.string.password_does_not_match)
            else -> null
        }

    }

    companion object {
        const val TAG = "ChangePasswordFragment"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observe()
    }

    private fun observe() {
        viewModel.changePasswordStatus.observe(viewLifecycleOwner) {
            if (it == true) {
                startActivity(Intent(requireActivity(), AuthActivity::class.java))
            }
            viewModel.changePasswordStatus.postValue(null)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.btnChange.isEnabled = !it
            if (it == true) {
                binding.pgLoading.show()
            } else
                binding.pgLoading.hide()
        }

    }


    private fun handleUI() {
        binding.edtOldPassword.addTextChangedListener(changePasswordTextWatcher)
        binding.edtPassword.addTextChangedListener(changePasswordTextWatcher)
        binding.edtConfirmPassword.addTextChangedListener(changePasswordTextWatcher)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnChange.setOnClickListener {
            if (changePasswordEnable) {
                viewModel.changePassword(
                    binding.edtOldPassword.text.toString(),
                    binding.edtPassword.text.toString(),
                    binding.edtConfirmPassword.text.toString()
                )
            }
        }
    }

}