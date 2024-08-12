package com.ahuynh.muzimusicapp.ui.component.user.song.love

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentLoveSongBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.user.song.SongViewModel
import com.ahuynh.muzimusicapp.ui.component.user.song.menu.SongMenu
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoveSongFragment : BaseFragment<FragmentLoveSongBinding>(FragmentLoveSongBinding::inflate), SongAdapter.OnSongClicked {

    companion object {
        const val TAG = "LoveSongFragment"
    }

    private lateinit var songAdapter: SongAdapter
    private val viewModel by viewModels<SongViewModel>()
    private lateinit var songOfType: ArrayList<Song>

    override fun onResume() {
        super.onResume()
        loadData()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
    }

    private fun loadData() {
        viewModel.getLoveSong()
    }

    private fun observeViewModel() {
        viewModel.loveSong.observe(viewLifecycleOwner) { songs ->
            songAdapter.submitList(songs)
            songOfType = songs as ArrayList<Song>
            binding.rcySongs.visibility = if (songs.isEmpty()) View.GONE else View.VISIBLE
            binding.tvNoSongs.visibility = if (songs.isEmpty()) View.VISIBLE else View.GONE
            binding.shimmer.stopShimmer()
            binding.shimmer.visibility = View.GONE
            binding.swipeRefresh.isRefreshing = false
        }

        viewModel.des.observe(viewLifecycleOwner) { description ->
            binding.tvDes.text = description
        }
    }

    private fun setupUI() {
        binding.swipeRefresh.setOnRefreshListener {
            loadData()

        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }


        songAdapter = SongAdapter(this)
        binding.rcySongs.adapter = songAdapter
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.tvNoSongs.visibility = if(songOfType.isEmpty()) View.VISIBLE else View.GONE
                    songAdapter.submitList(songOfType)
                } else {
                    filterSongs(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

    }
    private fun filterSongs(query: String) {
        val filteredList = songOfType.filter { song ->
            song.name.contains(query, ignoreCase = true)
        }
        binding.tvNoSongs.visibility = if (filteredList.isEmpty() || songOfType.isEmpty()) View.VISIBLE else View.GONE
        songAdapter.submitList(filteredList)
    }


    private fun startPlayerActivity(song: Song, songList: ArrayList<Song>) {
        Utils.sendNewMusic(requireActivity(), MusicService.ACTION_PLAY, song, songList)
    }

    override fun onSongClicked(song: Song) {
        startPlayerActivity(song, songOfType)
    }

    override fun openMenu(song: Song) {
        val fragment = SongMenu()
        fragment.arguments = Bundle().apply {
            putParcelable(Constants.SONG,song)
        }
        fragment.show(requireActivity().supportFragmentManager,null)
    }
}