package com.ahuynh.muzimusicapp.ui.component.main.playlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.PlaylistAdapter
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.databinding.FragmentPlaylistBinding
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.BaseDialogBottomSheetFragment
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaylistFragment : BaseDialogBottomSheetFragment(),
    PlaylistAdapter.OnPlaylistClicked {
    companion object {
        const val TAG = "PlaylistFragment"
    }

    private val viewModel by viewModels<PlaylistViewModel>({ requireActivity() })
    private val playlistAdapter = PlaylistAdapter(this)
    private lateinit var binding: FragmentPlaylistBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observe()

    }



    private fun handleUI() {
        binding.rcyPlaylist.adapter = playlistAdapter

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnAdd.setOnClickListener {
            val action = PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistAddFragment()
            findNavController().navigate(action)


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

        viewModel.addPlaylistStatus.observe(viewLifecycleOwner) {
            it?.let {
               viewModel.addPlaylistStatus.postValue(null)
            }

        }


        viewModel.updatePlaylistStatus.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.updatePlaylistStatus.postValue(null)
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
            PlaylistFragmentDirections.actionPlaylistFragmentToDetailPlaylistFragment(playlist)
        findNavController().navigate(action)
    }

    override fun onMoreItemClicked(playlist: Playlist) {
        val action =
            PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistModelBottomSheet(playlist)
        findNavController().navigate(action)
    }


}