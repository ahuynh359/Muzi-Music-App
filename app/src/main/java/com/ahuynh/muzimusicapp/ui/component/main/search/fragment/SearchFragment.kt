package com.ahuynh.muzimusicapp.ui.component.main.search.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.home.AlbumHomeAdapter
import com.ahuynh.muzimusicapp.adapter.home.SingerHomeAdapter
import com.ahuynh.muzimusicapp.adapter.home.SongHomeAdapter
import com.ahuynh.muzimusicapp.adapter.home.TypeAdapter
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.databinding.FragmentHomeBinding
import com.ahuynh.muzimusicapp.databinding.FragmentSearchBinding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.main.home.detail.DetailTypeFragment
import com.ahuynh.muzimusicapp.ui.component.main.search.SearchActivity
import com.ahuynh.muzimusicapp.utils.Constants
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
       val fragment = DetailTypeFragment()
        fragment.arguments = Bundle().apply {
            putParcelable(Constants.TYPE, type)
        }
        fragment.show(requireActivity().supportFragmentManager,null)
    }

}
