package com.ahuynh.muzimusicapp.ui.component.playlist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.adapter.PlaylistAdapter
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.databinding.FragmentPlaylistBinding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.playlist.detail.PlaylistAddDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaylistFragment : BaseFragment<FragmentPlaylistBinding>(FragmentPlaylistBinding::inflate),
    PlaylistAdapter.OnPlaylistClicked {
    companion object {
        const val TAG = "PlaylistFragment"
    }

    private val viewModel by viewModels<PlaylistViewModel>({requireActivity()})
    private val playlistAdapter = PlaylistAdapter(this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        handleUI()
        observe()

    }

    private fun getData() {
        viewModel.getAllPlaylist()
    }

    private fun handleUI() {
        binding.rcyPlaylist.adapter = playlistAdapter

        binding.btnAdd.setOnClickListener {
            val bundle = bundleOf("playlist" to null)
            val dialogFragment = PlaylistAddDialog()
            dialogFragment.arguments = bundle
            dialogFragment.show(parentFragmentManager, PlaylistAddDialog.TAG)


        }

    }

    private fun toggleSort() {
//        sortingAsc = !sortingAsc
//        if (sortingAsc) {
//            binding.btnAZ.text = "A - Z"
//            viewModel.getAllPlaylist(Constants.SortingOrder.ASCENDING)
//        } else {
//            binding.btnAZ.text = "Z - A"
//            viewModel.getAllPlaylist(Constants.SortingOrder.DESCENDING)
//        }


    }

    private fun observe() {

        viewModel.playlists.observe(viewLifecycleOwner) { response ->
            playlistAdapter.submitList(response)
            binding.rcyPlaylist.visibility = View.VISIBLE
            hideShimmer()
        }

        viewModel.mess.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
//        viewModel.addPlaylistStatus.observe(viewLifecycleOwner) {
//            getData()
//        }
//        viewModel.updatePlaylistStatus.observe(viewLifecycleOwner) {
//            getData()
//        }
//
//        viewModel.deletePlaylistStatus.observe(viewLifecycleOwner) {
//            getData()
//        }
//        viewModel.message.observe(viewLifecycleOwner) {
//            if (it != null) {
//                makeErrorToast(requireContext(), it)
//            }
//
//        }


    }

    private fun hideShimmer() {
        binding.rcyPlaylist.visibility = View.VISIBLE
        binding.shimmerPlaylist.stopShimmer()
        binding.shimmerPlaylist.visibility = View.GONE
    }


    override fun onPlaylistClicked(playlist: Playlist) {
//        val action =
//            PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistDetailFragment(playlist)
//        findNavController().navigate(action)
    }

    override fun onMoreItemClicked(playlist: Playlist) {
//        val action =
//            PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistModelBottomSheet(playlist)
//        findNavController().navigate(action)
    }


}