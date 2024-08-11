package com.ahuynh.muzimusicapp.ui.component.user.recent

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.SongEntityAdapter
import com.ahuynh.muzimusicapp.data.database.entity.SongEntity
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentRecentBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.user.song.menu.SongMenu
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentFragment :
    BaseFragment<FragmentRecentBinding>(FragmentRecentBinding::inflate),
    SongEntityAdapter.OnSongEntityClick {

    companion object {
        const val TAG = "RecentFragment"
    }

    private val songAdapter = SongEntityAdapter(this, SongEntityAdapter.TYPE_SONG_ENTITY_RECENTLY)
    private val viewModel by viewModels<RecentViewModel>()
    private var recentSongList: ArrayList<Song> = arrayListOf()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
        observe()
        getData()

    }

    private fun getData() {
        viewModel.getRecentSongs()
    }

    private fun observe() {
        viewModel.recentSong.observe(viewLifecycleOwner) {
            binding.rcySongs.visibility = View.VISIBLE
            songAdapter.submitList(it)
            recentSongList = it as ArrayList<Song>
            binding.shimmer.stopShimmer()
            binding.shimmer.visibility = View.INVISIBLE
        }
    }

    private fun handleUI() {
        binding.rcySongs.adapter = songAdapter
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    override fun onSongEntityClick(songEntity: SongEntity) {
        val song = songEntity.toSong()
        Utils.sendNewMusic(
            requireActivity(),
            MusicService.ACTION_PLAY,
            song, arrayListOf(song)
        )
    }

    override fun openMenu(songEntity: SongEntity) {
        val fragment = SongMenu()
        fragment.arguments = Bundle().apply {
            putParcelable(Constants.SONG, songEntity.toSong())
        }
        fragment.show(requireActivity().supportFragmentManager, null)
    }


}