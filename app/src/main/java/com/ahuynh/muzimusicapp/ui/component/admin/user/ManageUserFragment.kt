package com.ahuynh.muzimusicapp.ui.component.admin.user

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.UserAdapter
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.databinding.FragmentManageUserBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ManageUserFragment :
    BaseFragment<FragmentManageUserBinding>(FragmentManageUserBinding::inflate),
    UserAdapter.OnUserClicked {

    private val viewModel by viewModels<ManageUserViewModel>({ requireActivity() })

    companion object {
        const val TAG = "ManageUserFragment"
    }

    private val userAdapter = UserAdapter(this)

    private var userList: ArrayList<User> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        Log.d("ABC","do nay")
        viewModel.getAllUser()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observe()


    }

    private fun observe() {
        viewModel.userList.observe(viewLifecycleOwner) {
            binding.rcyUser.visibility = View.VISIBLE
            if (it != null) {
                userList = it as ArrayList<User>
                userAdapter.submitList(it)
            }
            binding.shimmer.stopShimmer()
            binding.shimmer.visibility = View.INVISIBLE


        }


    }


    private fun handleUI() {
        binding.rcyUser.adapter = userAdapter


        binding.edtSearch.setOnClickListener {
            val action =
                ManageUserFragmentDirections.actionManageUserFragmentToSearchManageUserFragment()
            findNavController().navigate(action)
        }

        binding.btnAdd.setOnClickListener {
            val action = ManageUserFragmentDirections.actionManageUserFragmentToAddUserFragment()
            findNavController().navigate(action)
        }


    }

    override fun onUserClicked(user: User) {
        val action = ManageUserFragmentDirections.actionManageUserFragmentToManageUserDetail(user)
        findNavController().navigate(action)
    }

    override fun onMoreClicked(user: User) {

    }


}
