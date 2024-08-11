package com.ahuynh.muzimusicapp.ui.component.auth.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.request.SignUpRequest
import com.ahuynh.muzimusicapp.databinding.FragmentSignupBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.utils.Utils
import com.royrodriguez.transitionbutton.TransitionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>(FragmentSignupBinding::inflate) {

    private val viewModel by viewModels<SignupViewModel>()
    private var isSignUpEnabled = false

    companion object {
        const val TAG = "SignupFragment"
    }

    private val signupTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            validateInputs()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeData()
    }

    private fun observeData() {
        viewModel.loginStatus.observe(viewLifecycleOwner) { loginStatus ->
            loginStatus?.let {
                handleSignUpResult(loginStatus)
            }
            viewModel.loginStatus.postValue(null)
        }
    }

    private fun setupUI() {
        binding.apply {
            edtUser.addTextChangedListener(signupTextWatcher)
            edtEmail.addTextChangedListener(signupTextWatcher)
            edtConfirmPassword.addTextChangedListener(signupTextWatcher)
            edtPassword.addTextChangedListener(signupTextWatcher)

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnSignUp.setOnClickListener {
                if (isSignUpEnabled) {
                    val signUpRequest = SignUpRequest(
                        email = edtEmail.text.toString().trim(),
                        password = edtPassword.text.toString().trim(),
                        confirmPassword = edtConfirmPassword.text.toString().trim(),
                        username = edtUser.text.toString().trim()
                    )
                    viewModel.signup(signUpRequest)
                    btnSignUp.startAnimation()
                }
            }
        }
    }

    private fun handleSignUpResult(isSuccess: Boolean) {
        if (isSuccess) {
            findNavController().popBackStack()
        } else {
            binding.btnSignUp.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null)
        }
        viewModel.mess?.let { message ->
            Utils.makeToast(requireContext(), message)
        }
    }

    private fun validateInputs() {
        val emailInput = binding.edtEmail.text.toString().trim()
        val passwordInput = binding.edtPassword.text.toString().trim()
        val confirmPasswordInput = binding.edtConfirmPassword.text.toString().trim()
        val usernameInput = binding.edtUser.text.toString().trim()

        isSignUpEnabled = emailInput.isNotEmpty() &&
                passwordInput.isNotEmpty() &&
                passwordInput.length >= 6 &&
                confirmPasswordInput.isNotEmpty() &&
                passwordInput == confirmPasswordInput &&
                usernameInput.isNotEmpty() &&
                Utils.isValidEmail(emailInput)

        binding.btnSignUp.isEnabled = isSignUpEnabled
        binding.btnSignUp.setBackgroundResource(if (isSignUpEnabled) R.drawable.btn_enable else R.drawable.btn_disable)

        binding.edtEmail.error = when {
            emailInput.isEmpty() -> getString(R.string.can_not_be_empty)
            !Utils.isValidEmail(emailInput) -> getString(R.string.invalid_email)
            else -> null
        }

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
        binding.edtUser.error =
            if (usernameInput.isEmpty()) getString(R.string.can_not_be_empty) else null
    }
}