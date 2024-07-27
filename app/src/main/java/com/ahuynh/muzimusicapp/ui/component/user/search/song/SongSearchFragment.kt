package com.ahuynh.muzimusicapp.ui.component.user.search.song

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentSongSearchBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.component.user.search.SearchViewModel
import com.ahuynh.muzimusicapp.ui.component.user.song.menu.SongMenu
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.ui.component.user.home.HomeFragmentDirections
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongSearchFragment : Fragment(), SongAdapter.OnSongClicked {

    private lateinit var binding: FragmentSongSearchBinding
    private val viewModel by viewModels<SearchViewModel>({ requireActivity() })

    private lateinit var songAdapter: SongAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSongSearchBinding.inflate(inflater, container, false)

        songAdapter = SongAdapter(this)
        binding.rcySong.apply {
            adapter = songAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.songs.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.tvNoResult.visibility = View.VISIBLE
                binding.rcySong.visibility = View.GONE
            } else {
                songAdapter.submitList(it)

                binding.tvNoResult.visibility = View.GONE
                binding.rcySong.visibility = View.VISIBLE
            }
        }

        return binding.root
    }


    override fun onSongClicked(song: Song) {
        viewModel.songs.value?.let {
            startActivity(Intent(context, PlayerActivity::class.java))
            Utils.sendMusic(
                requireContext(),
                MusicService.ACTION_PLAY,
                song,
                ArrayList(it)
            )


        }
    }

    override fun openMenu(song: Song) {

    }

}