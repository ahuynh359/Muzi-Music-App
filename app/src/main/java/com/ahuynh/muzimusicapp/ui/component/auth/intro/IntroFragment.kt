package com.ahuynh.muzimusicapp.ui.component.auth.intro

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.request.LoginRequest
import com.ahuynh.muzimusicapp.databinding.FragmentIntroBinding
import com.ahuynh.muzimusicapp.databinding.FragmentLoginBinding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.auth.login.LoginViewModel
import com.ahuynh.muzimusicapp.utils.Utils.isValidEmail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroFragment : BaseFragment<FragmentIntroBinding>(FragmentIntroBinding::inflate) {


    companion object {
        const val TAG = "IntroFragment"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()

    }

    private fun handleUI() {
        binding.btnLogIn.setOnClickListener {
            startAction(IntroFragmentDirections.actionIntroFragmentToLoginFragment())
        }
        binding.btnSignUpFree.setOnClickListener {
            startAction(IntroFragmentDirections.actionIntroFragmentToSignupFragment())

        }
    }

    private fun startAction(action: NavDirections) {
        findNavController().navigate(action)
    }


}