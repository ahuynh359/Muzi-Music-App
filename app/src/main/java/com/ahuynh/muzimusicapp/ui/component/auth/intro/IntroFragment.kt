package com.ahuynh.muzimusicapp.ui.component.auth.intro

import android.os.Bundle
import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.databinding.FragmentIntroBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
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