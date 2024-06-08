package com.ahuynh.muzimusicapp.ui.component.activity.search.album

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.AlbumAdapter
import com.ahuynh.muzimusicapp.adapter.PlaylistAdapter
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.databinding.FragmentAlbumSearchBinding
import com.ahuynh.muzimusicapp.ui.component.activity.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumSearchFragment : Fragment(),  AlbumAdapter.OnAlbumAdapterClicked{

    private lateinit var binding: FragmentAlbumSearchBinding
    private val viewModel by viewModels<SearchViewModel>({ requireActivity() })

    private lateinit var albumAdapter: AlbumAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumSearchBinding.inflate(inflater, container, false)
        albumAdapter = AlbumAdapter(this)


        binding.recyclerView.apply {
            adapter = albumAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.albums.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.tvNoResult.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                albumAdapter.submitList(it)

                binding.tvNoResult.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        }

        return binding.root
    }



    override fun onAlbumClicked(album: Album) {
        TODO("Not yet implemented")
    }

    override fun onMoreItemAlbumClicked(album: Album) {
        TODO("Not yet implemented")
    }

}