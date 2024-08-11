package com.ahuynh.muzimusicapp.ui.component.auth.changepassword

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.databinding.FragmentEmailBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailFragment : BaseFragment<FragmentEmailBinding>(FragmentEmailBinding::inflate) {
    companion object {
        const val TAG = "EmailFragment"
    }

    private val viewModel by viewModels<ResetPasswordViewModel>({ requireActivity() })
    private var isSendEnable = false
    private val sendTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {
            validateInputs()
        }

    }

    private fun validateInputs() {
        val emailInput = binding.edtEmail.text.toString().trim()
        isSendEnable = emailInput.isNotEmpty() && Utils.isValidEmail(emailInput)
        binding.btnSend.isEnabled = isSendEnable
        binding.btnSend.setBackgroundResource(
            if (isSendEnable) R.drawable.btn_enable else R.drawable.btn_disable
        )
        binding.edtEmail.error = when {
            emailInput.isEmpty() -> "Cannot be empty"
            !Utils.isValidEmail(emailInput) -> "Invalid email address"
            else -> null
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
        observeData()
    }


    private fun observeData() {
        viewModel.sendEmailStatus.observe(viewLifecycleOwner) { status ->
            status?.let {
                handleSendEmailStatus(it)
            }
            viewModel.sendEmailStatus.postValue(null)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.pbLoading.show()
            } else binding.pbLoading.hide()
        }
    }

    private fun handleUI() {
        binding.apply {
            edtEmail.addTextChangedListener(sendTextWatcher)
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnSend.setOnClickListener {
                if (isSendEnable) {
                    viewModel.email = binding.edtEmail.text.toString()
                    viewModel.sendEmail()
                }
            }
        }

    }

    private fun handleSendEmailStatus(status: Boolean) {
        if (status) {
            val action =
                EmailFragmentDirections.actionForgotPasswordFragmentToChangePasswordFragment()
            findNavController().navigate(action)
        } else {
            viewModel.mess?.let {
                Utils.makeToast(requireContext(), it)
            }
        }

    }


}

