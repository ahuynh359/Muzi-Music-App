package com.ahuynh.muzimusicapp.ui.component.user.playlist.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.databinding.DialogModelBottomSheetPlaylistBinding
import com.ahuynh.muzimusicapp.ui.component.user.playlist.PlaylistViewModel
import com.ahuynh.muzimusicapp.ui.base.dialog.ConfirmDialog
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils.parcelable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaylistMenu : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "PlaylistMenu"
    }

    private lateinit var binding: DialogModelBottomSheetPlaylistBinding
    private val viewModel by viewModels<PlaylistViewModel>({ requireActivity() })
    private lateinit var currentPlaylist: Playlist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentPlaylist = PlaylistMenuArgs.fromBundle(requireArguments()).playlist
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogModelBottomSheetPlaylistBinding.inflate(inflater, container, false)
        setupUI()
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        viewModel.updatePlaylistStatus.observe(viewLifecycleOwner) {
            if (it == true) dismiss()
        }
    }

    private fun setupUI() {
        binding.btnDelete.setOnClickListener {
            showDeleteConfirmDialog()
        }

        binding.btnEdit.setOnClickListener {
            navigateToEditPlaylist()
        }
    }

    private fun showDeleteConfirmDialog() {
        ConfirmDialog(
            requireContext(),
            title = "Delete playlist",
            message = "Are you sure you want to delete ${currentPlaylist.name}?",
            negativeButtonTitle = "CANCEL",
            positiveButtonTitle = "DELETE",
            callback = object : ConfirmDialog.ConfirmCallBack {
                override fun negativeAction() {
                    dismiss()
                }

                override fun positiveAction() {
                    viewModel.deletePlaylist(currentPlaylist.id)
                    dismiss()
                }
            }
        ).show()
    }

    private fun navigateToEditPlaylist() {
        val action = PlaylistMenuDirections.actionPlaylistModelBottomSheetToPlaylistEditFragment(currentPlaylist)
        findNavController().navigate(action)
    }
}