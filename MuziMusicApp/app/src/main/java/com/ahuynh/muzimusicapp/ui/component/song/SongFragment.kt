package com.ahuynh.muzimusicapp.ui.component.song

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.adapter.OnSongClicked
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentSongBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Utils
import com.itextpdf.io.codec.brotli.dec.Dictionary.getData
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


        handleUI()
        observe()

    }



    private fun handleUI() {
        binding.rcySong.adapter = songAdapter
        binding.swipe.setOnRefreshListener {
            observe()
        }

    }

    private fun observe() {
        viewModel.getAllSongs()
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSongClicked(song: Song) {
        viewModel.updateSongListen(song)
        viewModel.updateSongWithCurrentDate(song, Utils.getCurrentDateAsString())
        getData()

        startActivity(Intent(requireContext(), PlayerActivity::class.java))
        Utils.sendMusic(
            requireContext(),
            MusicService.ACTION_PLAY,
            song, listSong
        )



    }

    override fun openMenu(song: Song) {
        TODO("Not yet implemented")
    }

}
