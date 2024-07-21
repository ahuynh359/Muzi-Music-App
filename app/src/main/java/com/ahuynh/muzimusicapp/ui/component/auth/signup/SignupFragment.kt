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
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>(FragmentSignupBinding::inflate) {

    private val viewModel by viewModels<SignupViewModel>()
    private var isSignUpEnable = false

    companion object {
        const val TAG = "SignupFragment"
    }

    private val loginTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            updateSignUpButtonState()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeData()
    }

    private fun observeData() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.btnSignUp.isEnabled = !isLoading
            if (isLoading) binding.pbLoading.show() else binding.pbLoading.hide()
        }

        viewModel.status.observe(viewLifecycleOwner) { status ->
            if (status == true) {
                findNavController().popBackStack()
                showToast("Create account successfully")
            } else {
                viewModel.mess?.let { showToast(it) }
            }
        }
    }

    private fun setupUI() {
        binding.apply {
            edtUser.addTextChangedListener(loginTextWatcher)
            edtEmail.addTextChangedListener(loginTextWatcher)
            edtConfirmPassword.addTextChangedListener(loginTextWatcher)
            edtPassword.addTextChangedListener(loginTextWatcher)

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnSignUp.setOnClickListener {
                if (isSignUpEnable) {
                    val signUpRequest = SignUpRequest(
                        edtEmail.text.toString(),
                        edtPassword.text.toString(),
                        edtConfirmPassword.text.toString(),
                        edtUser.text.toString()
                    )
                    viewModel.signup(signUpRequest)
                }
            }
        }
    }

    private fun updateSignUpButtonState() {
        val emailInput = binding.edtEmail.text.toString().trim()
        val passwordInput = binding.edtPassword.text.toString().trim()
        val confirmPasswordInput = binding.edtConfirmPassword.text.toString().trim()
        val usernameInput = binding.edtUser.text.toString().trim()

        isSignUpEnable = emailInput.isNotEmpty() && passwordInput.isNotEmpty() && confirmPasswordInput.isNotEmpty() && usernameInput.isNotEmpty()

        binding.btnSignUp.setBackgroundResource(if (isSignUpEnable) R.drawable.btn_enable else R.drawable.btn_disable)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}