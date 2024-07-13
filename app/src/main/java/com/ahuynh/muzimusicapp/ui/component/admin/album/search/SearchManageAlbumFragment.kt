package com.ahuynh.muzimusicapp.ui.component.admin.album.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.AlbumAdapter
import com.ahuynh.muzimusicapp.adapter.TypeAdapter
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.databinding.FragmentSearchManageAlbumBinding
import com.ahuynh.muzimusicapp.databinding.FragmentSearchManageTypeBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.album.ManageAlbumViewModel
import com.ahuynh.muzimusicapp.ui.component.admin.type.ManageTypeViewModel
import com.ahuynh.muzimusicapp.ui.component.admin.type.search.SearchManageTypeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchManageAlbumFragment : BaseFragment<FragmentSearchManageAlbumBinding>(
    FragmentSearchManageAlbumBinding::inflate) ,
    AlbumAdapter.OnAlbumClicked{

    private val viewModel by viewModels<ManageAlbumViewModel>({ requireActivity() })
    private val albumAdapter = AlbumAdapter(this)
    private var albumList: ArrayList<Album> = arrayListOf()

    companion object {
        const val TAG = "SearchManageAlbumFragment"
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNewAlbums()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observeData()


    }

    private fun handleUI() {
        binding.rcyAlbum.adapter = albumAdapter
        binding.edtSearch.clearFocus()
        binding.edtSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    albumAdapter.submitList(albumList)

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
        val searchAlbumList = mutableListOf<Album>()
        for (s in albumList) {
            if (s.name.lowercase().contains(query.lowercase())
            ) {
                searchAlbumList.add(s)
            }
        }
        if (searchAlbumList.isEmpty()) {
            albumAdapter.submitList(arrayListOf())
            binding.tvNoAlbum.visibility = View.VISIBLE
        } else {
            binding.tvNoAlbum.visibility = View.INVISIBLE
            albumAdapter.submitList(searchAlbumList)
        }
    }

    private fun observeData() {
        viewModel.albumList.observe(viewLifecycleOwner) {
            binding.rcyAlbum.visibility = View.VISIBLE
            if (it != null) {
                albumList = it as ArrayList<Album>
                albumAdapter.submitList(it)
            }


        }

    }



    override fun onAlbumClicked(album: Album) {
        TODO("Not yet implemented")
    }

    override fun onMoreItemAlbumClicked(album: Album) {
        TODO("Not yet implemented")
    }

}