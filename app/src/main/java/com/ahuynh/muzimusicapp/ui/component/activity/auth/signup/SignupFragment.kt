package com.ahuynh.muzimusicapp.ui.component.activity.auth.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.data.model.request.SignUpRequest
import com.ahuynh.muzimusicapp.databinding.FragmentSignupBinding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.utils.Utils.isValidEmail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>(FragmentSignupBinding::inflate) {
    private val viewModel by viewModels<SignupViewModel>()

    companion object {
        const val TAG = "SignupFragment"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
        observeData()

    }

    private fun observeData() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.btnSignUp.isEnabled = !it
            if (it == true) {
                binding.pbLoading.show()
            } else
                binding.pbLoading.hide()
        }

        viewModel.status.observe(viewLifecycleOwner) {
            if (it == true) {
                val action = SignupFragmentDirections.actionSignupFragmentToOtpFragment()
                findNavController().navigate(action)
                Toast.makeText(requireContext(), "Create account successfully ", Toast.LENGTH_LONG)
                    .show()
            } else
                if (viewModel.mess != null)
                    Toast.makeText(requireContext(), viewModel.mess, Toast.LENGTH_LONG).show()
        }
    }

    private fun handleUI() {


        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnSignUp.setOnClickListener {
            if (checkError()) {
                val signUpRequest = SignUpRequest(
                    binding.edtEmail.text.toString(),
                    binding.edtPassword.text.toString(),
                    binding.edtUserName.text.toString()
                )
                viewModel.signup(signUpRequest)
            }
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

        if (binding.edtPassword.text.toString().length < 6) {
            binding.tilPassword.error = "Password > 6 character"
            return false
        }


        binding.tilPassword.error = ""

        if (binding.edtUserName.text.toString().isEmpty()) {
            binding.tilUserName.error = "Do not leave empty"
            return false
        }
        binding.tilUserName.error = ""

        return true

    }


}
