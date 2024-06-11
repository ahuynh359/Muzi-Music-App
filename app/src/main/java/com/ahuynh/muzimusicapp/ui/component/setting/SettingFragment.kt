package com.ahuynh.muzimusicapp.ui.component.setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.databinding.FragmentSettingBinding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.activity.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {
    companion object {
        const val TAG = "SettingFragment"
    }

    private val viewModel by viewModels<SettingViewModel>({ requireActivity() })


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleUI()


    }

    private fun handleUI() {
        binding.btnLogOut.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(requireContext(), AuthActivity::class.java))
            activity?.finish()
        }
    }
}