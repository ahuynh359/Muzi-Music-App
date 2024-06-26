package com.ahuynh.muzimusicapp.ui.component.auth.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.request.SignUpRequest
import com.ahuynh.muzimusicapp.databinding.FragmentSignupBinding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.utils.Utils.isValidEmail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>(FragmentSignupBinding::inflate) {
    private val viewModel by viewModels<SignupViewModel>()
    private var isSignUpEnable = false
    private val loginTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            val emailInput = binding.edtEmail.text.toString().trim()
            val passwordInput = binding.edtPassword.text.toString().trim()
            val confirmPasswordInput = binding.edtConfirmPassword.text.toString().trim()
            val usernameInput = binding.edtUser.text.toString().trim()
            isSignUpEnable =
                emailInput.isNotEmpty() && passwordInput.isNotEmpty() && confirmPasswordInput.isNotEmpty() && usernameInput.isNotEmpty()
            if (isSignUpEnable) {
                binding.btnSignUp.setBackgroundResource(R.drawable.btn_enable)
            } else
                binding.btnSignUp.setBackgroundResource(R.drawable.btn_disable)
        }

    }

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
                findNavController().popBackStack()
                Toast.makeText(requireContext(), "Create account successfully ", Toast.LENGTH_LONG)
                    .show()
            } else
                if (viewModel.mess != null)
                    Toast.makeText(requireContext(), viewModel.mess, Toast.LENGTH_LONG).show()
        }
    }

    private fun handleUI() {

        binding.edtUser.addTextChangedListener(loginTextWatcher)
        binding.edtEmail.addTextChangedListener(loginTextWatcher)
        binding.edtConfirmPassword.addTextChangedListener(loginTextWatcher)
        binding.edtPassword.addTextChangedListener(loginTextWatcher)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnSignUp.setOnClickListener {
            if (isSignUpEnable) {
                val signUpRequest = SignUpRequest(
                    binding.edtEmail.text.toString(),
                    binding.edtPassword.text.toString(),
                    binding.edtConfirmPassword.text.toString(),
                    binding.edtUser.text.toString()
                )
                viewModel.signup(signUpRequest)
            }
        }

    }


}
