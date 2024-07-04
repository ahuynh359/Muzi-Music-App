package com.ahuynh.muzimusicapp.ui.component.main.playlist.add_new_playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.databinding.FragmentPlaylistAddBinding
import com.ahuynh.muzimusicapp.ui.base.dialog_fragment.BaseDialogFragment
import com.ahuynh.muzimusicapp.ui.component.main.playlist.PlaylistViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaylistAddFragment : BaseDialogFragment() {
    private var _binding: FragmentPlaylistAddBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PlaylistViewModel>({ requireActivity() })

    companion object {
        const val TAG = "PlaylistAddFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaylistAddBinding.inflate(inflater, container, false)
        handleUI()
        observeData()
        return binding.root
    }

    private fun observeData() {
        viewModel.addPlaylistStatus.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    this.dismiss()
                } else {
                    Toast.makeText(requireContext(), viewModel.mess, Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }

    }

    private fun handleUI() {
        binding.btnCancle.setOnClickListener {
            this.dismiss()
        }

        binding.btnCreate.setOnClickListener {
            val playlist = getCurrentPlaylist()
            playlist?.let {
                viewModel.addNewPlaylist(playlist)
            }


        }


    }

    private fun getCurrentPlaylist(): String? {
        if (binding.edtPlaylist.text.toString().isEmpty()) {
            binding.edtPlaylist.error = "Playlist name is empty"
            return null
        }
        return binding.edtPlaylist.text.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}