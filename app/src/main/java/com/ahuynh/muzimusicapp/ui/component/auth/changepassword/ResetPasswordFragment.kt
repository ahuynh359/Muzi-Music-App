package com.ahuynh.muzimusicapp.ui.component.auth.changepassword

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.request.ResetPasswordRequest
import com.ahuynh.muzimusicapp.databinding.FragmentResetPasswordBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment :
    BaseFragment<FragmentResetPasswordBinding>(FragmentResetPasswordBinding::inflate) {

    companion object {
        const val TAG = "ResetPasswordFragment"
    }

    private val viewModel by viewModels<ResetPasswordViewModel>({ requireActivity() })
    private var isSendEnable = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
        observe()

        viewModel.timeLeftInMillis.value = 60000
        viewModel.startTimer()
    }


    override fun onPause() {
        super.onPause()
        viewModel.resetTimer()
    }

    override fun onResume() {
        super.onResume()
        viewModel.resumeTimer()
    }


    private fun observe() {
        viewModel.changePasswordStatus.observe(viewLifecycleOwner) { status ->
            status?.let {
                handlePasswordChangeStatus(it)
            }
            viewModel.changePasswordStatus.postValue(null)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading == true) {
                binding.pbLoading.show()
            } else {
                binding.pbLoading.hide()
            }
        }


        viewModel.timeLeftInMillis.observe(viewLifecycleOwner) {
            val text = Utils.formatTime(it)
            binding.tvTime.text = text

        }
    }

    private fun handlePasswordChangeStatus(status: Boolean) {
        if (status) {
            val action =
                ResetPasswordFragmentDirections.actionResetPasswordFragmentToLoginFragment()
            findNavController().navigate(action)
        } else {
            viewModel.mess?.let {
                Utils.makeToast(requireContext(), it)
            }
        }
    }


    private val sendTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            validateInputs()

        }
    }

    private fun validateInputs() {
        val passwordInput = binding.edtPassword.text.toString().trim()
        val confirmPasswordInput = binding.edtConfirmPassword.text.toString().trim()

        isSendEnable =
            passwordInput.isNotEmpty() &&
                    passwordInput.length >= 6 &&
                    confirmPasswordInput.isNotEmpty() &&
                    passwordInput == confirmPasswordInput

        binding.btnOk.isEnabled = isSendEnable
        binding.btnOk.setBackgroundResource(if (isSendEnable) R.drawable.btn_enable else R.drawable.btn_disable)


        binding.edtPassword.error = when {
            passwordInput.isEmpty() -> getString(R.string.can_not_be_empty)
            passwordInput.length < 6 -> getString(R.string.password_must_be_at_least_6_characters)
            else -> null
        }
        binding.edtConfirmPassword.error = when {
            confirmPasswordInput.isEmpty() -> getString(R.string.can_not_be_empty)
            confirmPasswordInput != passwordInput -> getString(R.string.password_does_not_match)
            else -> null
        }

    }


    private fun handleUI() {
        binding.edtPassword.addTextChangedListener(sendTextWatcher)
        binding.edtConfirmPassword.addTextChangedListener(sendTextWatcher)
        setupClickListeners()
    }


    private fun setupClickListeners() {
        binding.btnOk.setOnClickListener {
            if (isSendEnable) {
                val otp = binding.otpView.otp
                otp?.let {
                    viewModel.changePassword(
                        ResetPasswordRequest(
                            otp,
                            binding.edtPassword.text.toString().trim(),
                            binding.edtConfirmPassword.text.toString().trim()
                        )
                    )
                }
            }
        }


        binding.btnBack.setOnClickListener {
            findNavController().popBackStack(R.id.forgotPasswordFragment, true)
        }
    }


}