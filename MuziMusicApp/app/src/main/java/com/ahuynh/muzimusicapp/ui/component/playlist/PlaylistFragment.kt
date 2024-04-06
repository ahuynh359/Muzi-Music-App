package com.ahuynh.muzimusicapp.ui.component.playlist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.databinding.FragmentPlaylistBinding
import com.ahuynh.muzimusicapp.model.playlist.Playlist
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaylistFragment : BaseFragment<FragmentPlaylistBinding>(FragmentPlaylistBinding::inflate),
    OnPlaylistClicked {
    companion object {
        const val TAG = "PlaylistFragment"
    }

    private val viewModel by viewModels<PlaylistViewModel>({requireActivity()})
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
            val bundle = bundleOf("playlist" to null)
            val dialogFragment = PlaylistAddDialog()
            dialogFragment.arguments = bundle
            dialogFragment.show(parentFragmentManager, "PlaylistAddDialog")


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

        viewModel.playlists.observe(viewLifecycleOwner) { response ->
            playlistAdapter.submitList(response)
            binding.rcyPlaylist.visibility = View.VISIBLE
            hideShimmer()
        }
        viewModel.addPlaylistStatus.observe(viewLifecycleOwner) {
            viewModel.getAllPlaylist(Constants.SortingOrder.ASCENDING)
        }
        viewModel.updatePlaylistStatus.observe(viewLifecycleOwner) {
            viewModel.getAllPlaylist(Constants.SortingOrder.ASCENDING)
        }

        viewModel.deletePlaylistStatus.observe(viewLifecycleOwner) {
            viewModel.getAllPlaylist(Constants.SortingOrder.ASCENDING)
        }

        viewModel.message.observe(viewLifecycleOwner) { response ->
            response?.let {
                Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()
            }

        }


    }

    private fun hideShimmer() {
        binding.rcyPlaylist.visibility = View.VISIBLE
        binding.shimmerPlaylist.stopShimmer()
        binding.shimmerPlaylist.visibility = View.GONE
    }


    override fun onPlaylistClicked(playlist: Playlist) {
        val action =
            PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistDetailFragment(playlist)
        findNavController().navigate(action)
    }

    override fun onMoreItemClicked(playlist: Playlist) {
        val bundle = bundleOf("playlist" to playlist)
        val dialogFragment = PlaylistModelBottomSheet()
        dialogFragment.arguments = bundle
        dialogFragment.show(parentFragmentManager, PlaylistAddDialog.TAG)
    }


}
