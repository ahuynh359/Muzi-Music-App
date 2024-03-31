package com.ahuynh.muzimusicapp.ui.component.song

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahuynh.muzimusicapp.databinding.FragmentSongBinding
import com.ahuynh.muzimusicapp.model.Song
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongFragment : BaseFragment<FragmentSongBinding>(FragmentSongBinding::inflate),
    OnSongClicked {

    private val viewModel by viewModels<SongViewModel>()
    private val TAG = "SongFragment"
    private val songAdapter = SongAdapter(this)
    private var sortingAsc = true
    private var isLinear = true
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager
    private var listSong: ArrayList<Song> = arrayListOf()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linearLayoutManager = LinearLayoutManager(context)
        gridLayoutManager = GridLayoutManager(context, 2)

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
        binding.btnGrid.setOnClickListener {
            toggleLayout()
        }

    }

    private fun toggleLayout() {
        isLinear = !isLinear
        if (isLinear) {
            binding.rcySong.layoutManager = linearLayoutManager
        } else
            binding.rcySong.layoutManager = gridLayoutManager
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
        viewModel.songs.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Loading -> {
                }

                is Response.Success -> {
                    val list = response.data
                    songAdapter.submitList(list)
                    binding.rcySong.visibility = View.VISIBLE
                    listSong = list as ArrayList<Song>
                    hideShimmer()
                }

                is Response.Failure -> {
                    hideShimmer()
                    Toast.makeText(context, "Error at server side", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, response.errorMessage)
                }
            }
        }
    }

    private fun hideShimmer() {
        binding.shimmerSong.stopShimmer()
        binding.shimmerSong.visibility = View.GONE
    }

    override fun onSongClicked(song: Song) {
        Toast.makeText(context, song.id.toString(), Toast.LENGTH_SHORT).show()
        startActivity(Intent(requireContext(), PlayerActivity::class.java))
        Utils.sendMusic(
            requireContext(),
            Constants.ACTION_PLAY,
            song, listSong
        )
    }

}
