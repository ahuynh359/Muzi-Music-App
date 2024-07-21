package com.ahuynh.muzimusicapp.ui.component.auth.changepassword

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.databinding.FragmentEmailBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailFragment : BaseFragment<FragmentEmailBinding>(FragmentEmailBinding::inflate) {
    private val viewModel by viewModels<ResetPasswordViewModel>({ requireActivity() })
    private var isSendEnable = false
    private val sendTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            val emailInput = binding.edtEmail.text.toString().trim()
            isSendEnable = emailInput.isNotEmpty()
            if (isSendEnable) {
                binding.btnSend.setBackgroundResource(R.drawable.btn_enable)
            } else binding.btnSend.setBackgroundResource(R.drawable.btn_disable)
        }

    }


    companion object {
        const val TAG = "EmailFragment"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
        observeData()


    }


    private fun observeData() {
        viewModel.sendEmailStatus.observe(viewLifecycleOwner) { status ->
            status?.let {
                handleSendEmailStatus(it)
            }
            viewModel.sendEmailStatus.postValue(null)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.pbLoading.show()
            } else binding.pbLoading.hide()
        }
    }

    private fun handleUI() {
        binding.edtEmail.addTextChangedListener(sendTextWatcher)
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSend.setOnClickListener {
            if (isSendEnable) {
                viewModel.email = binding.edtEmail.text.toString()
                viewModel.sendEmail()
            }
        }


    }

    private fun handleSendEmailStatus(status: Boolean) {
        if (status) {
            val action = EmailFragmentDirections.actionForgotPasswordFragmentToChangePasswordFragment()
            findNavController().navigate(action)
        } else {

            if (viewModel.mess != null) {
                Toast.makeText(requireContext(), viewModel.mess, Toast.LENGTH_SHORT).show()
            }
        }

    }


}

