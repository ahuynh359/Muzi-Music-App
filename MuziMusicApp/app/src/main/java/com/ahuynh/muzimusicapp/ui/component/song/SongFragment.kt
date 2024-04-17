package com.ahuynh.muzimusicapp.ui.component.song

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.adapter.OnSongClicked
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentSongBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongFragment : BaseFragment<FragmentSongBinding>(FragmentSongBinding::inflate),
    OnSongClicked {

    private val viewModel by viewModels<SongViewModel>()

    companion object {
        const val TAG = "SongFragment"
    }
    private val songAdapter = SongAdapter(this)
    private var listSong: ArrayList<Song> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        handleUI()
        observe()

    }

    private fun getData() {
        viewModel.getAllSongs()
    }

    private fun handleUI() {
        binding.rcySong.adapter = songAdapter
        binding.swipe.setOnRefreshListener {
            getData()
        }

    }

    private fun observe() {
        viewModel.songList.observe(viewLifecycleOwner) {
            binding.swipe.isRefreshing = false
            songAdapter.submitList(it)
            binding.rcySong.visibility = View.VISIBLE
            listSong = it as ArrayList<Song>
            hideShimmer()

        }



    }

    private fun hideShimmer() {
        binding.shimmerSong.stopShimmer()
        binding.shimmerSong.visibility = View.GONE
    }

    override fun onSongClicked(song: Song) {
        viewModel.updateSongListen(song)
        getData()

        startActivity(Intent(requireContext(), PlayerActivity::class.java))
        Utils.sendMusic(
            requireContext(),
            MusicService.ACTION_PLAY,
            song, listSong
        )



    }

}
