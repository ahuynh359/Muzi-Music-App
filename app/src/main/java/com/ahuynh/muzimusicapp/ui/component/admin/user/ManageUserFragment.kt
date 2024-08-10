package com.ahuynh.muzimusicapp.ui.component.admin.user

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.UserAdapter
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.databinding.FragmentManageUserBinding
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortBottomSheetFragment
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.type.ManageTypeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ManageUserFragment :
    BaseFragment<FragmentManageUserBinding>(FragmentManageUserBinding::inflate),
    UserAdapter.OnUserClicked, SortBottomSheetFragment.SortOptionListener {

    private val viewModel by viewModels<ManageUserViewModel>({ requireActivity() })

    companion object {
        const val TAG = "ManageUserFragment"
    }

    private val userAdapter = UserAdapter(this)

    private var userList: ArrayList<User> = arrayListOf()

    override fun onResume() {
        super.onResume()
        viewModel.getAllUsers()
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
                if (it.isEmpty()) {
                    binding.tvNoUser.visibility = View.VISIBLE
                } else
                    binding.tvNoUser.visibility = View.INVISIBLE
            }
            binding.shimmer.stopShimmer()
            binding.shimmer.visibility = View.INVISIBLE


        }
        viewModel.sortUser.observe(viewLifecycleOwner){
            binding.btnSort.text = it.name
            viewModel.getAllUsers()
        }


    }




    private fun handleUI() {
        binding.rcyUser.adapter = userAdapter


        binding.btnAdd.setOnClickListener {
            val action = ManageUserFragmentDirections.actionManageUserFragmentToAddUserFragment()
            findNavController().navigate(action)
        }

        binding.btnSort.setOnClickListener {
            val sortBottomSheet = SortBottomSheetFragment()
            sortBottomSheet.listener = this
            val args = Bundle()
            args.putBoolean("IS_USER_MANAGE_FRAGMENT", true)
            sortBottomSheet.arguments = args
            sortBottomSheet.show(parentFragmentManager, null)
        }

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.tvNoUser.visibility = View.GONE
                    userAdapter.submitList(userList)
                } else {
                    filterUsers(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })





    }
    private fun filterUsers(query: String) {
        val filteredList = userList.filter { user ->
            user.username.contains(query, ignoreCase = true) || user.email.contains(query, ignoreCase = true)
        }
        binding.tvNoUser.visibility = if (filteredList.isEmpty()) View.VISIBLE else View.GONE
        userAdapter.submitList(filteredList)
    }

    override fun onUserClicked(user: User) {
        val action = ManageUserFragmentDirections.actionManageUserFragmentToManageUserDetail(user)
        findNavController().navigate(action)
    }

    override fun onMoreClicked(user: User) {
        val action =
            ManageUserFragmentDirections.actionManageUserFragmentToManageUserMenu(user)
        findNavController().navigate(action)
    }

    override fun onSortOptionSelected(name: SortName) {
        when (name) {
            SortName.NEW -> {
                viewModel.setSortUser(SortName.NEW)
            }

            SortName.OLD -> {
                viewModel.setSortUser(SortName.OLD)
            }

            SortName.A_Z -> {
                viewModel.setSortUser(SortName.A_Z)

            }

            SortName.Z_A -> {
                viewModel.setSortUser(SortName.Z_A)
            }

            SortName.LOCKED -> {
                viewModel.setSortUser(SortName.LOCKED)
            }

            SortName.UNLOCKED -> {
                viewModel.setSortUser(SortName.UNLOCKED)
            }

        }
    }


}
