package com.ahuynh.muzimusicapp.ui.component.user.changpassword

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.databinding.FragmentChangePasswordBinding
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.BaseDialogBottomSheetFragment
import com.ahuynh.muzimusicapp.ui.component.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordBottomSheetFragment : BaseDialogBottomSheetFragment() {

    private lateinit var binding: FragmentChangePasswordBinding
    private var changePasswordEnable = false
    private val viewModel by viewModels<ChangePasswordViewModel>()
    private val changePasswordTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            val oldPasswordInput = binding.edtOldPassword.text.toString().trim()
            val passwordInput = binding.edtPassword.text.toString().trim()
            val confirmPasswordInput = binding.edtConfirmPassword.text.toString().trim()
            changePasswordEnable =
                oldPasswordInput.isNotEmpty() && passwordInput.isNotEmpty() && confirmPasswordInput.isNotEmpty()
            if (changePasswordEnable) {
                binding.btnChange.setBackgroundResource(R.drawable.btn_enable)
            } else
                binding.btnChange.setBackgroundResource(R.drawable.btn_disable)
        }

    }

    companion object {
        const val TAG = "ChangePasswordFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observe()
//        getData()
    }

    private fun observe() {
        viewModel.status.observe(viewLifecycleOwner) {
            if (it == true) {
                startActivity(Intent(requireActivity(), AuthActivity::class.java))
            } else
                if (viewModel.mess != null)
                    Toast.makeText(requireContext(), viewModel.mess, Toast.LENGTH_LONG).show()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.btnChange.isEnabled = !it
            if (it == true) {
                binding.pgLoading.show()
            } else
                binding.pgLoading.hide()
        }

    }


    private fun handleUI() {
        binding.edtOldPassword.addTextChangedListener(changePasswordTextWatcher)
        binding.edtPassword.addTextChangedListener(changePasswordTextWatcher)
        binding.edtConfirmPassword.addTextChangedListener(changePasswordTextWatcher)

        binding.btnBack.setOnClickListener {
            dismiss()
        }
        binding.btnChange.setOnClickListener {
            if (changePasswordEnable) {
                viewModel.changePassword(
                    binding.edtOldPassword.text.toString(),
                    binding.edtPassword.text.toString(),
                    binding.edtConfirmPassword.text.toString()
                )
            }
        }
    }

}