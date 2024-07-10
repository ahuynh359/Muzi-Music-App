package com.ahuynh.muzimusicapp.ui.component.admin.user.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.UserAdapter
import com.ahuynh.muzimusicapp.adapter.home.TypeAdapter
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.databinding.FragmentSearchBinding
import com.ahuynh.muzimusicapp.databinding.FragmentSearchManageUserBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.user.ManageUserViewModel
import com.ahuynh.muzimusicapp.ui.component.user.search.SearchActivity
import com.ahuynh.muzimusicapp.ui.component.user.search.fragment.SearchFragmentDirections
import com.ahuynh.muzimusicapp.ui.component.user.search.fragment.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchManageUserFragment : BaseFragment<FragmentSearchManageUserBinding>(FragmentSearchManageUserBinding::inflate) ,
    UserAdapter.OnUserClicked{

    private val viewModel by viewModels<ManageUserViewModel>({ requireActivity() })
    private val userAdapter = UserAdapter(this)
    private var userList: ArrayList<User> = arrayListOf()

    companion object {
        const val TAG = "SearchManageUserFragment"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observeData()


    }

    private fun handleUI() {
        binding.rcyUser.adapter = userAdapter
        binding.edtSearch.clearFocus()
        binding.edtSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    userAdapter.submitList(userList)

                } else
                    performSearch(newText)
                return true
            }
        })

        binding.tvCancle.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun performSearch(query: String) {
        val searchUserList = mutableListOf<User>()
        for (s in userList) {
            if (s.username.lowercase().contains(query.lowercase()) || s.email.lowercase()
                    .contains(query.lowercase())
            ) {
                searchUserList.add(s)
            }
        }
        if (searchUserList.isEmpty()) {
            userAdapter.submitList(arrayListOf())
            binding.tvNoUser.visibility = View.VISIBLE
        } else {
            binding.tvNoUser.visibility = View.INVISIBLE
            userAdapter.submitList(searchUserList)
        }
    }

    private fun observeData() {
        viewModel.userList.observe(viewLifecycleOwner) {
            binding.rcyUser.visibility = View.VISIBLE
            if (it != null) {
                userList = it as ArrayList<User>
                userAdapter.submitList(it)
            }


        }

    }




    override fun onUserClicked(user: User) {
    }

    override fun onMoreClicked(user: User) {
    }

}
