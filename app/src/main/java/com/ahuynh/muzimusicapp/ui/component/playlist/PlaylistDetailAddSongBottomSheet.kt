package com.ahuynh.muzimusicapp.ui.component.playlist

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.ahuynh.muzimusicapp.databinding.DialogModelBottomSheetAddSongBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PlaylistDetailAddSongBottomSheet : BottomSheetDialogFragment() {
    companion object {
        const val TAG = "PlaylistDetailAddSongBottomSheet"
    }

    //private lateinit var adapter: SongAddAdapter
    private lateinit var binding: DialogModelBottomSheetAddSongBinding

    //private val viewModel by viewModels<PlaylistViewModel>({ requireActivity() })
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogModelBottomSheetAddSongBinding.inflate(
            inflater,
            container,
            false
        )

        //handleUI()
        return binding.root
    }

    //Full dialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener {

            val bottomSheet = dialog.findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet
            )
            val behavior = BottomSheetBehavior.from(bottomSheet)
            val layoutParams = bottomSheet.layoutParams
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            bottomSheet.layoutParams = layoutParams
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        return dialog
    }

}

//        adapter = SongAddAdapter(currentPlaylist,this)
//        adapter.setData(Constants.SONG_Old_LIST_DATA)


//        binding.rcySong.adapter = adapter
//        val listSongToAdd = mutableListOf<String>()
//        binding.btnOk.setOnClickListener {
//            for((indexSong, i) in adapter.checkboxStates().withIndex()){
//                if(i){
//                    listSongToAdd.add(Constants.SONG_Old_LIST_DATA.get(indexSong).id!!)
//                }
//            }
//
//            viewModel.addSongsToPlaylist(listSongToAdd,currentPlaylist)
//            viewModel.addSongToPlaylistStatus.observe(viewLifecycleOwner){
//                if(it){
//                    dismiss()
//                } else
//                    makeErrorToast(requireContext(),"Cannot add song to playlist")
//            }
//
//        }
//
//    }





