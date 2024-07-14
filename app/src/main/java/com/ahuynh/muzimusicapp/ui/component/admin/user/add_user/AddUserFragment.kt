package com.ahuynh.muzimusicapp.ui.component.admin.user.add_user

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.request.AddUserRequest
import com.ahuynh.muzimusicapp.data.model.request.LoginRequest
import com.ahuynh.muzimusicapp.databinding.FragmentAddUserBinding
import com.ahuynh.muzimusicapp.databinding.FragmentLoginBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.AdminActivity
import com.ahuynh.muzimusicapp.ui.component.admin.user.ManageUserViewModel
import com.ahuynh.muzimusicapp.ui.component.auth.login.LoginFragmentDirections
import com.ahuynh.muzimusicapp.ui.component.auth.login.LoginViewModel
import com.ahuynh.muzimusicapp.ui.component.user.UserActivity
import com.ahuynh.muzimusicapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddUserFragment : BaseFragment<FragmentAddUserBinding>(FragmentAddUserBinding::inflate) {
    private val viewModel by viewModels<ManageUserViewModel>({ requireActivity() })
    private var isCreateOk = false

    private val loginTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            val username = binding.edtUserName.text.toString().trim()
            val isEmail = Utils.isValidEmail(email)
            isCreateOk = email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty() && isEmail
            if(!isEmail){
                binding.edtEmail.error = "Email is invalid"
            } else {
                binding.edtEmail.error = null
            }
            if(password.length<6){
                binding.edtPassword.error = "Password must be at least 6 characters"
            } else {
                binding.edtPassword.error = null
            }
            if (isCreateOk) {
                binding.btnAdd.setBackgroundResource(R.drawable.btn_enable)
            } else
                binding.btnAdd.setBackgroundResource(R.drawable.btn_disable)
        }

    }

    companion object {
        const val TAG = "AddUserFragment"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
        observeData()

    }

    private fun observeData() {
        viewModel.createUserStatus.observe(viewLifecycleOwner) {
            it?.let {
                    if(it==true){
                        findNavController().popBackStack()
                    }
                    viewModel.mess?.let { mess ->
                        Toast.makeText(requireContext(), mess, Toast.LENGTH_LONG).show()
                    }

            }
            viewModel.createUserStatus.postValue(null)
        }


        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.btnAdd.isEnabled = !it
            if (it == true) {
                binding.pgLoading.show()
            } else
                binding.pgLoading.hide()
        }


    }

    private fun handleUI() {

        binding.edtEmail.addTextChangedListener(loginTextWatcher)
        binding.edtPassword.addTextChangedListener(loginTextWatcher)
        binding.edtUserName.addTextChangedListener(loginTextWatcher)

        binding.btnAdd.setOnClickListener {
            if (isCreateOk) {

                val addUserRequest = AddUserRequest(
                    binding.edtEmail.text.toString().trim(),
                    binding.edtPassword.text.toString(),
                    binding.edtUserName.text.toString()
                )
                viewModel.createUser(addUserRequest)


            }

        }


        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }


    }




}
