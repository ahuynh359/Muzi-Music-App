package com.ahuynh.muzimusicapp.ui.component.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.request.LoginRequest
import com.ahuynh.muzimusicapp.databinding.FragmentLoginBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.AdminActivity
import com.ahuynh.muzimusicapp.ui.component.user.UserActivity
import com.royrodriguez.transitionbutton.TransitionButton
import com.royrodriguez.transitionbutton.TransitionButton.OnAnimationStopEndListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel by viewModels<LoginViewModel>()
    private var isLoginEnable = false

    private val loginTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            val emailInput = binding.edtEmail.text.toString().trim()
            val passwordInput = binding.edtPassword.text.toString().trim()
            isLoginEnable = emailInput.isNotEmpty() && passwordInput.isNotEmpty()
            if (isLoginEnable) {
                binding.btnLogIn.setBackgroundResource(R.drawable.btn_enable)
            } else
                binding.btnLogIn.setBackgroundResource(R.drawable.btn_disable)
        }

    }



    companion object {
        const val TAG = "LoginFragment"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
        observeData()

    }

    private fun observeData() {
        viewModel.loginStatus.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    if (viewModel.isAdmin())
                        startActivityAndFinishCurrent(AdminActivity::class.java)
                    else
                        startActivityAndFinishCurrent(UserActivity::class.java)
                } else {
                    viewModel.mess?.let { mess ->
                        Toast.makeText(requireContext(), mess, Toast.LENGTH_LONG).show()
                    }
                    binding.btnLogIn.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null)
                }

            }
            viewModel.loginStatus.postValue(null)
        }


//        viewModel.isLoading.observe(viewLifecycleOwner) {
//            binding.btnLogIn.isEnabled = !it
//            if (it == true) {
//                binding.pbLoading.show()
//            } else
//                binding.pbLoading.hide()
//
//        }


    }

    private fun handleUI() {

        binding.edtEmail.addTextChangedListener(loginTextWatcher)
        binding.edtPassword.addTextChangedListener(loginTextWatcher)

        binding.btnLogIn.setOnClickListener {
            if (isLoginEnable) {
                val loginRequest = LoginRequest(
                    binding.edtEmail.text.toString().trim(),
                    binding.edtPassword.text.toString()
                )
                viewModel.login(loginRequest)


                binding.btnLogIn.startAnimation()
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

    private fun startActivityAndFinishCurrent(destinationActivity: Class<*>) {
        binding.btnLogIn.stopAnimation(
            TransitionButton.StopAnimationStyle.EXPAND
        ) {
            val intent = Intent(requireActivity(), destinationActivity)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            requireActivity().finish()
        }

    }


}
