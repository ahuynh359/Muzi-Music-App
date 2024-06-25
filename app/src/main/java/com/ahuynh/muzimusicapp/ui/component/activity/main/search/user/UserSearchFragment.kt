package com.ahuynh.muzimusicapp.ui.component.activity.main.search.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahuynh.muzimusicapp.adapter.UserAdapter
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.databinding.FragmentUserSearchBinding
import com.ahuynh.muzimusicapp.ui.component.activity.main.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserSearchFragment : Fragment(), UserAdapter.OnUserClicked {

    private lateinit var binding: FragmentUserSearchBinding
    private val viewModel by viewModels<SearchViewModel>({ requireActivity() })

    private lateinit var userAdapter: UserAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserSearchBinding.inflate(inflater, container, false)

        userAdapter = UserAdapter(this)
        binding.recyclerView.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.users.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.tvNoResult.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                userAdapter.submitList(it)

                binding.tvNoResult.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        }

        return binding.root
    }


    override fun onUserClicked(user: User) {
        Toast.makeText(requireContext(), "ABC", Toast.LENGTH_SHORT).show()
    }

}