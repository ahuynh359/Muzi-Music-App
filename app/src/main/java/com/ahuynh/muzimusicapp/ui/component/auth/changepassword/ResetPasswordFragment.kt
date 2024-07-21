package com.ahuynh.muzimusicapp.ui.component.auth.changepassword

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.request.ResetPasswordRequest
import com.ahuynh.muzimusicapp.databinding.FragmentResetPasswordBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class ResetPasswordFragment :
    BaseFragment<FragmentResetPasswordBinding>(FragmentResetPasswordBinding::inflate) {

    private val viewModel by viewModels<ResetPasswordViewModel>({ requireActivity() })
    private var isSendEnable = false

    companion object {
        const val TAG = "ResetPasswordFragment"
    }


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
            if (viewModel.mess != null) {
                Toast.makeText(requireContext(), viewModel.mess, Toast.LENGTH_LONG).show()
            }
        }
    }


    private val sendTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            val password = binding.edtPassword.text.toString().trim()
            val confirmPassword = binding.edtConfirmPassword.text.toString().trim()
            isSendEnable = password.isNotEmpty() && confirmPassword.isNotEmpty()
            updateSendButtonState()
        }
    }

    private fun updateSendButtonState() {
        binding.btnOk.setBackgroundResource(if (isSendEnable) R.drawable.btn_enable else R.drawable.btn_disable)
    }

    private fun handleUI() {
        binding.edtPassword.addTextChangedListener(sendTextWatcher)
        binding.edtConfirmPassword.addTextChangedListener(sendTextWatcher)
        setupOtpEditTexts()
        setupClickListeners()
    }

    private fun setupOtpEditTexts() {
        val editTexts = arrayOf(
            binding.edtOne,
            binding.edtTwo,
            binding.edtThree,
            binding.edtFour,
            binding.edtFive,
            binding.edtSix
        )

        editTexts.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                private var beforeText: String = ""

                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                    beforeText = s.toString()
                }


                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1 && index < editTexts.size - 1) {
                        editTexts[index + 1].requestFocus()
                    } else if (s?.length == 0 && beforeText.isNotEmpty() && index > 0) {
                        editTexts[index - 1].requestFocus()
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    private fun setupClickListeners() {
        binding.btnOk.setOnClickListener {
            if (isSendEnable) {
                val otp = collectOtp()
                viewModel.changePassword(
                    ResetPasswordRequest(
                        otp,
                        binding.edtPassword.text.toString().trim(),
                        binding.edtConfirmPassword.text.toString().trim()
                    )
                )
            }
        }


        binding.btnBack.setOnClickListener {
            findNavController().popBackStack(R.id.forgotPasswordFragment, true)
        }
    }

    private fun collectOtp(): String {
        val editTexts = arrayOf(
            binding.edtOne,
            binding.edtTwo,
            binding.edtThree,
            binding.edtFour,
            binding.edtFive,
            binding.edtSix
        )
        return editTexts.joinToString(separator = "") { it.text.toString() }
    }
}