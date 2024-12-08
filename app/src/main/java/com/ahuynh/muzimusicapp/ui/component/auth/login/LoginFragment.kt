package com.ahuynh.muzimusicapp.ui.component.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.data.model.request.LoginRequest
import com.ahuynh.muzimusicapp.databinding.FragmentLoginBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.AdminActivity
import com.ahuynh.muzimusicapp.ui.component.user.UserActivity
import com.ahuynh.muzimusicapp.utils.Utils
import com.royrodriguez.transitionbutton.TransitionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel by viewModels<LoginViewModel>()
    private var isLoginEnabled = false

    private val loginTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            validateInputs()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeLoginStatus()
    }

    private fun observeLoginStatus() {
        viewModel.loginStatus.observe(viewLifecycleOwner) { isSuccess ->
            isSuccess?.let {
                handleLoginResult(it)
            }
            viewModel.loginStatus.postValue(null)
        }
    }

    private fun setupUI() {
        binding.edtEmail.addTextChangedListener(loginTextWatcher)
        binding.edtPassword.addTextChangedListener(loginTextWatcher)

        binding.btnLogIn.setOnClickListener {
            if (isLoginEnabled) {
                login()
            }
        }

        binding.tvForgotPassword.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
            findNavController().navigate(action)
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun validateInputs() {
        val emailInput = binding.edtEmail.text.toString().trim()
        val passwordInput = binding.edtPassword.text.toString().trim()

        isLoginEnabled =
            emailInput.isNotEmpty() && passwordInput.isNotEmpty()

        binding.btnLogIn.isEnabled = isLoginEnabled

        binding.edtEmail.error = when {
            emailInput.isEmpty() -> "Cannot be empty"
            else -> null
        }

        binding.edtPassword.error = if (passwordInput.isEmpty()) "Cannot be empty" else null
    }

    private fun login() {
        val loginRequest = LoginRequest(
            binding.edtEmail.text.toString().trim(),
            binding.edtPassword.text.toString()
        )
        viewModel.login(loginRequest)
        binding.btnLogIn.startAnimation()
    }

    private fun handleLoginResult(isSuccess: Boolean) {
        if (isSuccess) {
            navigateToNextScreen()
        } else {
            binding.btnLogIn.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null)
            viewModel.mess?.let { message ->
                Utils.makeToast(requireContext(), message)
            }
        }
    }

    private fun navigateToNextScreen() {
        val destinationActivity =
            if (viewModel.isAdmin()) AdminActivity::class.java else UserActivity::class.java
        startActivityAndFinishCurrent(destinationActivity)
    }

    private fun startActivityAndFinishCurrent(destinationActivity: Class<*>) {
        binding.btnLogIn.stopAnimation(TransitionButton.StopAnimationStyle.EXPAND) {
            startActivity(Intent(
                requireActivity(),
                destinationActivity
            ).apply { addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION) })
            requireActivity().finish()
        }

    }
}