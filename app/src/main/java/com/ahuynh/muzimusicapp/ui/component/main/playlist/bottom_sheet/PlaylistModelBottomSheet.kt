package com.ahuynh.muzimusicapp.ui.component.main.playlist.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.databinding.DialogModelBottomSheetPlaylistBinding
import com.ahuynh.muzimusicapp.ui.component.main.playlist.PlaylistViewModel
import com.ahuynh.muzimusicapp.ui.base.dialog.ConfirmDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaylistModelBottomSheet : BottomSheetDialogFragment() {
    companion object {
        const val TAG = "ModalBottomSheetDialog"
    }

    private lateinit var binding: DialogModelBottomSheetPlaylistBinding
    private val viewModel by viewModels<PlaylistViewModel>({ requireActivity() })
    private lateinit var currentPlaylist: Playlist


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogModelBottomSheetPlaylistBinding.inflate(
            inflater,
            container,
            false
        )
        currentPlaylist = PlaylistModelBottomSheetArgs.fromBundle(requireArguments()).playlist

        handleUI()
        observe()
        return binding.root
    }

    private fun observe() {
        viewModel.updatePlaylistStatus.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    this.dismiss()
                }
            }

        }
    }

    private fun handleUI() {
        binding.btnDelete.setOnClickListener {
            ConfirmDialog(
                requireContext(),
                title = "Delete playlist",
                message = "Are you sure want to delete ${currentPlaylist.name} ?",
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

        binding.btnEdit.setOnClickListener {
            val action =
                PlaylistModelBottomSheetDirections.actionPlaylistModelBottomSheetToPlaylistEditFragment(currentPlaylist)
            findNavController().navigate(action)

        }
    }


}