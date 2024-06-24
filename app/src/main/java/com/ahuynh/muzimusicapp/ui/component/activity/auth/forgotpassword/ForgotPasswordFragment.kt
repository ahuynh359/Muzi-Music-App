package com.ahuynh.muzimusicapp.ui.component.activity.auth.forgotpassword

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.request.ForgotPasswordRequest
import com.ahuynh.muzimusicapp.data.model.request.ResendOtpRequest
import com.ahuynh.muzimusicapp.databinding.FragmentForgotPasswordBinding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.activity.main.MainActivity
import com.ahuynh.muzimusicapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment :
    BaseFragment<FragmentForgotPasswordBinding>(FragmentForgotPasswordBinding::inflate) {
    private val viewModel by viewModels<ForgotPasswordViewModel>()
    private var isSendEnable = false
    private val sendTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            val emailInput = binding.edtEmail.text.toString().trim()
            isSendEnable = emailInput.isNotEmpty()
            if (isSendEnable) {
                binding.btnSend.setBackgroundResource(R.drawable.btn_enable)
            } else binding.btnSend.setBackgroundResource(R.drawable.btn_disable)
        }

    }


    companion object {
        const val TAG = "ForgotPasswordFragment"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
        observeData()


    }

    private fun observeData() {
        viewModel.status.observe(viewLifecycleOwner) {
            if (it == true) {
                val action = ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToChangePasswordFragment()
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

    private fun handleUI() {
        binding.edtEmail.addTextChangedListener(sendTextWatcher)
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSend.setOnClickListener {
            if (isSendEnable) {
                viewModel.forgotPassword(ForgotPasswordRequest(binding.edtEmail.text.toString()))
            }
        }


    }


}

