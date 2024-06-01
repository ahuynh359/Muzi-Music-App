package com.ahuynh.muzimusicapp.ui.component.activity.auth.otp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.databinding.FragmentOtpBinding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpFragment : BaseFragment<FragmentOtpBinding>(FragmentOtpBinding::inflate) {
    private val viewModel by viewModels<OtpViewModel>()

    companion object {
        const val TAG = "OtpFragment"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
        observeData()

    }

    private fun observeData() {
        viewModel.status.observe(viewLifecycleOwner) {
            if (it == true) {
                val action = OtpFragmentDirections.actionOtpFragmentToLoginFragment()
                findNavController().navigate(action)
            } else
                if (viewModel.mess != null)
                    Toast.makeText(requireContext(), viewModel.mess, Toast.LENGTH_LONG).show()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.btnOk.isEnabled = !it
            if (it == true) {
                binding.pbLoading.show()
            } else
                binding.pbLoading.hide()
        }
    }

    private fun handleUI() {

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnOk.setOnClickListener {
            if (checkError()) {
                var otp = binding.edtOne.text.toString()
                otp += binding.edtTwo.text.toString()
                otp += binding.edtThree.text.toString()
                otp += binding.edtFour.text.toString()
                otp += binding.edtFive.text.toString()
                otp += binding.edtSix.text.toString()
                Toast.makeText(requireContext(), otp, Toast.LENGTH_LONG).show()
                viewModel.verifyEmail(otp)
            }
        }
    }

    private fun checkError(): Boolean {
        if (binding.edtOne.text.toString().isEmpty()) {
            binding.edtOne.requestFocus();
            binding.edtOne.error = "Empty"
            return false;
        }

        if (binding.edtTwo.text.toString().isEmpty()) {
            binding.edtTwo.requestFocus();
            binding.edtTwo.error = "Empty"
            return false;
        }

        if (binding.edtThree.text.toString().isEmpty()) {
            binding.edtThree.requestFocus();
            binding.edtThree.error = "Empty"
            return false;
        }

        if (binding.edtFour.text.toString().isEmpty()) {
            binding.edtFour.requestFocus();
            binding.edtFour.error = "Empty"
            return false;
        }

        if (binding.edtFive.text.toString().isEmpty()) {
            binding.edtFive.requestFocus();
            binding.edtFive.error = "Empty"
            return false;
        }

        if (binding.edtSix.text.toString().isEmpty()) {
            binding.edtSix.requestFocus();
            binding.edtSix.error = "Empty"
            return false;
        }

        return true
    }

}