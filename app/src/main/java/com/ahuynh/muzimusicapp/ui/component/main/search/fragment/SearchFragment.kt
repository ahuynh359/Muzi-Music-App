package com.ahuynh.muzimusicapp.ui.component.main.search.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.home.TypeAdapter
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.databinding.FragmentSearchBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.main.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) ,
TypeAdapter.OnTypeClicked{

    private val viewModel by viewModels<SearchViewModel>()
    private val typeAdapter = TypeAdapter(this)
    private var typeList: ArrayList<Type> = arrayListOf()

    companion object {
        const val TAG = "SongFragment"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observeData()


    }

    private fun handleUI() {
        binding.rcyType.adapter = typeAdapter

        binding.edtSearch.setOnClickListener {
            startActivity(Intent(requireActivity(), SearchActivity::class.java))
        }
    }

    private fun observeData() {
      viewModel.typeList.observe(viewLifecycleOwner){
          typeAdapter.submitList(it)
          typeList = it as ArrayList<Type>
      }
    }

    override fun onTypeClicked(type: Type) {
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailTypeFragment(type))
    }

}
