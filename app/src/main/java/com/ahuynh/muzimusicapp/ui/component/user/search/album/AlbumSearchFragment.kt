package com.ahuynh.muzimusicapp.ui.component.user.search.album

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.adapter.AlbumAdapter
import com.ahuynh.muzimusicapp.adapter.AlbumAdapter.AlbumViewType
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.databinding.FragmentAlbumSearchBinding
import com.ahuynh.muzimusicapp.ui.component.user.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumSearchFragment : Fragment(),  AlbumAdapter.OnAlbumClicked{

    private lateinit var binding: FragmentAlbumSearchBinding
    private val viewModel by viewModels<SearchViewModel>({ requireActivity() })

    private val albumAdapter=  AlbumAdapter(this, AlbumViewType.LIST)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumSearchBinding.inflate(inflater, container, false)

        binding.rcyAlbum.adapter = albumAdapter


        viewModel.albums.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.tvNoResult.visibility = View.VISIBLE
                binding.rcyAlbum.visibility = View.GONE
            } else {
                albumAdapter.submitList(it)
                binding.tvNoResult.visibility = View.GONE
                binding.rcyAlbum.visibility = View.VISIBLE
            }
        }

        return binding.root
    }



    override fun onAlbumClicked(album: Album) {
    }

    override fun onMoreItemAlbumClicked(album: Album) {

    }

}