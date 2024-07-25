package com.ahuynh.muzimusicapp.ui.component.admin.singer.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.SingerAdapter
import com.ahuynh.muzimusicapp.adapter.SingerViewType
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.databinding.FragmentSearchManageSingerBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.singer.ManageSingerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchManageSingerFragment : BaseFragment<FragmentSearchManageSingerBinding>(
    FragmentSearchManageSingerBinding::inflate
),
    SingerAdapter.OnSingerClicked {

    private val viewModel by viewModels<ManageSingerViewModel>({ requireActivity() })
    private val singerAdapter = SingerAdapter(this,SingerViewType.LIST)
    private var singerList: ArrayList<Singer> = arrayListOf()

    companion object {
        const val TAG = "SearchManageSingerFragment"
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllSingers()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observeData()


    }

    private fun handleUI() {
        binding.rcySinger.adapter = singerAdapter
        binding.edtSearch.clearFocus()
        binding.edtSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    singerAdapter.submitList(singerList)
                    binding.tvNoSinger.visibility = View.INVISIBLE

                } else
                    performSearch(newText)
                return false
            }
        })

        binding.tvCancle.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun performSearch(query: String) {
        val searchSingerList = mutableListOf<Singer>()
        for (s in singerList) {
            if (s.name.lowercase().contains(query.lowercase())
            ) {
                searchSingerList.add(s)
            }
        }
        if (searchSingerList.isEmpty()) {
            singerAdapter.submitList(arrayListOf())
            binding.tvNoSinger.visibility = View.VISIBLE
        } else {
            binding.tvNoSinger.visibility = View.INVISIBLE
            singerAdapter.submitList(searchSingerList)
        }
    }

    private fun observeData() {
        viewModel.singerList.observe(viewLifecycleOwner) {
            binding.rcySinger.visibility = View.VISIBLE
            if (it != null) {
                singerList = it as ArrayList<Singer>
                singerAdapter.submitList(it)
            }


        }

    }


    override fun onSingerClicked(singer: Singer) {
        val action =
            SearchManageSingerFragmentDirections.actionSearchManageSingerFragmentToManageSingerDetailFragment(
                singer
            )
        findNavController().navigate(action)
    }



}