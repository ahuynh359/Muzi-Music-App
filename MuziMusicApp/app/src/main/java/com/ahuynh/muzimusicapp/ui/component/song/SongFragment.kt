package com.ahuynh.muzimusicapp.ui.component.song

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.adapter.OnSongClicked
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentSongBinding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Constants.SONG_LIST_DATA
import com.ahuynh.muzimusicapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongFragment : BaseFragment<FragmentSongBinding>(FragmentSongBinding::inflate),
    OnSongClicked {

    private val viewModel by viewModels<SongViewModel>({requireActivity()})

    companion object {
        const val TAG = "SongFragment"
    }
    private val songAdapter = SongAdapter(this)
    private var sortingAsc = true
    private var listSong: ArrayList<Song> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        handleUI()
        observe()

    }

    private fun getData() {
        viewModel.getAllSongs(Constants.SortingOrder.ASCENDING)
    }

    private fun handleUI() {
        binding.rcySong.adapter = songAdapter
        binding.btnAZ.setOnClickListener {
            toggleSort()
        }


    }


    private fun toggleSort() {
        sortingAsc = !sortingAsc
        if (sortingAsc) {
            binding.btnAZ.text = "A - Z"
            viewModel.getAllSongs(Constants.SortingOrder.ASCENDING)
        } else {
            binding.btnAZ.text = "Z - A"
            viewModel.getAllSongs(Constants.SortingOrder.DESCENDING)
        }
    }

    private fun observe() {
        viewModel.songList.observe(viewLifecycleOwner) {
            songAdapter.submitList(it)
            binding.rcySong.visibility = View.VISIBLE
            listSong = it as ArrayList<Song>
            SONG_LIST_DATA = it
            hideShimmer()

        }

        viewModel.message.observe(viewLifecycleOwner) { response ->
            response?.let {
                Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun hideShimmer() {
        binding.shimmerSong.stopShimmer()
        binding.shimmerSong.visibility = View.GONE
    }

    override fun onSongClicked(song: Song) {
        viewModel.updateSongListen(song)
        if (sortingAsc) {
            viewModel.getAllSongs(Constants.SortingOrder.ASCENDING)
        } else {
            viewModel.getAllSongs(Constants.SortingOrder.DESCENDING)
        }
        Toast.makeText(context, song.id.toString(), Toast.LENGTH_SHORT).show()
        startActivity(Intent(requireContext(), PlayerActivity::class.java))
        Utils.sendMusic(
            requireContext(),
            Constants.ACTION_PLAY,
            song, listSong
        )

    }

}
