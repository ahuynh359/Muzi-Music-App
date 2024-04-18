package com.ahuynh.muzimusicapp.ui.component.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.databinding.DialogModelBottomSheetPlaylistBinding
import com.ahuynh.muzimusicapp.ui.dialog.ConfirmDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PlaylistModelBottomSheet : BottomSheetDialogFragment() {
    companion object {
        const val TAG = "ModalBottomSheetDialog"
    }
    private lateinit var binding: DialogModelBottomSheetPlaylistBinding
    private val viewModel by viewModels<PlaylistViewModel>({ requireActivity() })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogModelBottomSheetPlaylistBinding.inflate(
            inflater,
            container,
            false
        )

        handleUI()
        return binding.root
    }

    private fun handleUI() {
        val currentPlaylist = PlaylistModelBottomSheetArgs.fromBundle(requireArguments()).playlist!!
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
                        viewModel.deletePlaylist(currentPlaylist)
                        dismiss()
                    }
                }
            ).show()
        }

        binding.btnEdit.setOnClickListener {
            val bundle = bundleOf("playlist" to currentPlaylist)
            val dialogFragment = PlaylistAddDialog()
            dialogFragment.arguments = bundle
            dialogFragment.show(parentFragmentManager, PlaylistAddDialog.TAG)
            dismiss()
        }
    }




}