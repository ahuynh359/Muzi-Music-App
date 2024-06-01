package com.ahuynh.muzimusicapp.ui.component.activity.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.data_api.model.request.LoginRequest
import com.ahuynh.muzimusicapp.databinding.FragmentLoginBinding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.activity.main.MainActivity
import com.ahuynh.muzimusicapp.utils.Utils.isValidEmail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel by viewModels<LoginViewModel>()

    companion object {
        const val TAG = "LoginFragment"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
        observeData()

    }

    private fun observeData() {
        viewModel.status.observe(viewLifecycleOwner) {
            if (it == true) {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
                activity?.finish()
            } else
                if(viewModel.mess != null)
                    Toast.makeText(requireContext(), "Password not match", Toast.LENGTH_LONG).show()
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            binding.btnLogIn.isEnabled = !it
            if(it == true){
                binding.pbLoading.show()
            } else
                binding.pbLoading.hide()
        }
    }

    private fun handleUI() {
        binding.btnLogIn.setOnClickListener {
            if (checkError()) {
                val loginRequest = LoginRequest(
                    binding.edtEmail.text.toString().trim(),
                    binding.edtPassword.text.toString()
                )
                viewModel.login(loginRequest)

            }
        }

        binding.tvSignUp.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignupFragment()
            findNavController().navigate(action)
        }

    }

    private fun checkError(): Boolean {
        if (binding.edtEmail.text.toString().isEmpty()) {
            binding.tilEmail.error = "Do not leave empty"
            return false
        }
        if (!isValidEmail(binding.edtEmail.text.toString())) {
            binding.tilEmail.error = "Email not in form"
            return false
        }
        binding.tilEmail.error = ""
        if (binding.edtPassword.text.toString().isEmpty()) {
            binding.tilPassword.error = "Do not leave empty"
            return false
        }


        binding.tilPassword.error = ""

        return true

    }
}
