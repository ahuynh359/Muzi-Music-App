package com.ahuynh.muzimusicapp.ui.component.activity.auth.forgotpassword

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.databinding.FragmentForgotPasswordBinding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.activity.main.MainActivity
import com.ahuynh.muzimusicapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment :
    BaseFragment<FragmentForgotPasswordBinding>(FragmentForgotPasswordBinding::inflate) {
    private val viewModel by viewModels<ForgotPasswordViewModel>()

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
                findNavController().popBackStack()
            } else
                if(viewModel.mess != null)
                    Toast.makeText(requireContext(), viewModel.mess, Toast.LENGTH_LONG).show()
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            binding.btnSend.isEnabled = !it
            if(it == true){
                binding.pbLoading.show()
            } else
                binding.pbLoading.hide()
        }
    }

    private fun handleUI() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSend.setOnClickListener {
            if (checkError()) {
                Toast.makeText(requireContext(),binding.edtEmail.text.toString(),Toast.LENGTH_LONG).show()
                viewModel.resendOtp(binding.edtEmail.text.toString())
            }
        }


    }

    private fun checkError(): Boolean {
        if (binding.edtEmail.text.toString().isEmpty()) {
            binding.tilEmail.error = "Do not leave empty"
            return false
        }
        if (!Utils.isValidEmail(binding.edtEmail.text.toString())) {
            binding.tilEmail.error = "Email not in form"
            return false
        }
        binding.tilEmail.error = ""


        return true

    }

}

