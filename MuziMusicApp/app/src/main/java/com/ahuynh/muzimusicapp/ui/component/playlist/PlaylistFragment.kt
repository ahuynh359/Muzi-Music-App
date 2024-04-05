package com.ahuynh.muzimusicapp.ui.component.playlist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.databinding.FragmentPlaylistBinding
import com.ahuynh.muzimusicapp.model.playlist.Playlist
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Response
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaylistFragment : BaseFragment<FragmentPlaylistBinding>(FragmentPlaylistBinding::inflate),
    OnPlaylistClicked {

    private val viewModel by viewModels<PlaylistViewModel>({requireActivity()})
    private val TAG = "PlaylistFragment"
    private val playlistAdapter = PlaylistAdapter(this)
    private var sortingAsc = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        handleUI()
        observe()

    }

    private fun getData() {
        viewModel.getAllPlaylist(Constants.SortingOrder.ASCENDING)
    }

    private fun handleUI() {
        binding.rcyPlaylist.adapter = playlistAdapter
        binding.btnAZ.setOnClickListener {
            toggleSort()
        }
        binding.btnAdd.setOnClickListener {
            FormPlaylistFragment().show(requireActivity().supportFragmentManager, null)
        }

    }

    private fun toggleSort() {
        sortingAsc = !sortingAsc
        if (sortingAsc) {
            binding.btnAZ.text = "A - Z"
            viewModel.getAllPlaylist(Constants.SortingOrder.ASCENDING)
        } else {
            binding.btnAZ.text = "Z - A"
            viewModel.getAllPlaylist(Constants.SortingOrder.DESCENDING)
        }

    }

    private fun observe() {
        viewModel.addPlaylistStatus.observe(viewLifecycleOwner) { res ->
            when (res) {
                is Response.Loading -> {}
                is Response.Success -> {
                    playlistAdapter.submitList(mutableListOf())
                    viewModel.getAllPlaylist(Constants.SortingOrder.ASCENDING)
                }
                is Response.Failure -> {}
            }
        }
        viewModel.playlists.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Loading -> {
                }
                is Response.Success -> {
                    val list = response.data
                    playlistAdapter.submitList(list)
                    binding.rcyPlaylist.visibility = View.VISIBLE
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
        binding.shimmerPlaylist.stopShimmer()
        binding.shimmerPlaylist.visibility = View.GONE
    }



    override fun onPlaylistClicked(playlist: Playlist) {
        Log.d(TAG, playlist.toString())
        val action =
            PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistDetailFragment(playlist)
        findNavController().navigate(action)
    }

}
