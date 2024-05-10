package com.ahuynh.muzimusicapp.ui.component.song

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentSongBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.ui.component.upload.UploadActivity
import com.ahuynh.muzimusicapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongFragment : BaseFragment<FragmentSongBinding>(FragmentSongBinding::inflate),
    SongAdapter.OnSongClicked {

    private val viewModel by viewModels<SongViewModel>({ requireActivity() })

    companion object {
        const val TAG = "SongFragment"
    }
    private val songAdapter = SongAdapter(this)
    private var listSong: ArrayList<Song> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observe()
        getData()

    }

    private fun getData() {
        viewModel.getAllSongs()
        viewModel.getUnreadNoti()

    }



    private fun handleUI() {
        binding.rcySong.adapter = songAdapter
        binding.swipe.setOnRefreshListener {
            getData()
        }

        binding.btnUpload.setOnClickListener {
            startActivity(Intent(requireContext(), UploadActivity::class.java))
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

        viewModel.deleteSong.observe(viewLifecycleOwner) {
            getData()
        }

        viewModel.loveSong.observe(viewLifecycleOwner) {
            getData()
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

        startActivity(Intent(requireContext(), PlayerActivity::class.java))
        Utils.sendMusic(
            requireContext(),
            MusicService.ACTION_PLAY,
            song, listSong
        )



    }

    override fun openMenu(song: Song) {
        val action = SongFragmentDirections.actionSongFragmentToSongMenuBottom(song)
        findNavController().navigate(action)

    }

}
