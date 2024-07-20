package com.ahuynh.muzimusicapp.ui.component.auth.changepassword

import android.os.Bundle
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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment :
    BaseFragment<FragmentResetPasswordBinding>(FragmentResetPasswordBinding::inflate) {
    private val viewModel by viewModels<ResetPasswordViewModel>()
    private var isSendEnable = false


    companion object {
        const val TAG = "FragmentResetPasswordBinding"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
        observeData()


    }

    private fun observeData() {
        viewModel.status.observe(viewLifecycleOwner) {
            if (it == true) {
                val action = ResetPasswordFragmentDirections.actionResetPasswordFragmentToLoginFragment()
                findNavController().navigate(action)
            } else if (viewModel.mess != null) Toast.makeText(
                requireContext(),
                viewModel.mess,
                Toast.LENGTH_LONG
            ).show()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.pbLoading.show()
            } else binding.pbLoading.hide()
        }
    }

    private val sendTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            val password = binding.edtPassword.text.toString().trim()
            val confirmPassword = binding.edtConfirmPassword.text.toString().trim()
            isSendEnable = password.isNotEmpty() && confirmPassword.isNotEmpty()
            if (isSendEnable ) {
                binding.btnOk.setBackgroundResource(R.drawable.btn_enable)
            } else binding.btnOk.setBackgroundResource(R.drawable.btn_disable)
        }

    }


    private fun handleUI() {

        binding.edtPassword.addTextChangedListener(sendTextWatcher)
        binding.edtConfirmPassword.addTextChangedListener(sendTextWatcher)

        val editTexts = arrayOf(
            binding.edtOne,
            binding.edtTwo,
            binding.edtThree,
            binding.edtFour,
            binding.edtFive,
            binding.edtSix
        )


        for (i in editTexts.indices) {
            editTexts[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1 && i < editTexts.size - 1) {
                        editTexts[i + 1].requestFocus()
                    }

                }

                override fun afterTextChanged(s: Editable?) {
                    // Not needed
                }
            })
        }

        binding.btnOk.setOnClickListener {
            if (isSendEnable ) {
                val otp = editTexts.joinToString(separator = "") { it.text.toString() }
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

            findNavController().popBackStack(R.id.forgotPasswordFragment,true)
        }
    }


}