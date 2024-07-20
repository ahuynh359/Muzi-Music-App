package com.ahuynh.muzimusicapp.ui.component.admin.type.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.TypeAdapter
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.databinding.FragmentSearchManageTypeBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.type.ManageTypeFragmentDirections
import com.ahuynh.muzimusicapp.ui.component.admin.type.ManageTypeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchManageTypeFragment : BaseFragment<FragmentSearchManageTypeBinding>(
    FragmentSearchManageTypeBinding::inflate) ,
    TypeAdapter.OnTypeClicked{

    private val viewModel by viewModels<ManageTypeViewModel>({ requireActivity() })
    private val typeAdapter = TypeAdapter(this)
    private var typeList: ArrayList<Type> = arrayListOf()

    companion object {
        const val TAG = "SearchManageTypeFragment"
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllTypes()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observeData()


    }

    private fun handleUI() {
        binding.rcyType.adapter = typeAdapter
        binding.edtSearch.clearFocus()
        binding.edtSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    typeAdapter.submitList(typeList)
                    binding.tvNoType.visibility = View.INVISIBLE

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
        val searchTypeList = mutableListOf<Type>()
        for (s in typeList) {
            if (s.name.lowercase().contains(query.lowercase())
            ) {
                searchTypeList.add(s)
            }
        }
        if (searchTypeList.isEmpty()) {
            typeAdapter.submitList(arrayListOf())
            binding.tvNoType.visibility = View.VISIBLE
        } else {
            binding.tvNoType.visibility = View.INVISIBLE
            typeAdapter.submitList(searchTypeList)
        }
    }

    private fun observeData() {
        viewModel.typeList.observe(viewLifecycleOwner) {
            binding.rcyType.visibility = View.VISIBLE
            if (it != null) {
                typeList = it as ArrayList<Type>
                typeAdapter.submitList(it)
            }


        }

    }




    override fun onTypeClicked(type: Type) {
        val action =
            SearchManageTypeFragmentDirections.actionSearchManageTypeFragmentToManageTypeDetailFragment(type)
        findNavController().navigate(action)
    }

    override fun onMoreClicked(type: Type) {
    }

}