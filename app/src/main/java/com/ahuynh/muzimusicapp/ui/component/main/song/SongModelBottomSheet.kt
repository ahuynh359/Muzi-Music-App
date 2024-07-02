package com.ahuynh.muzimusicapp.ui.component.main.song

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.MenuAdapter
import com.ahuynh.muzimusicapp.adapter.SettingAdapter
import com.ahuynh.muzimusicapp.data.model.ItemMenu
import com.ahuynh.muzimusicapp.data.model.ItemMenuName
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.model.SettingItem
import com.ahuynh.muzimusicapp.data.model.SettingName
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.DialogModelBottomSheetPlaylistBinding
import com.ahuynh.muzimusicapp.databinding.FragmentSongModelBottomSheetBinding
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.BaseDialogBottomSheetFragment
import com.ahuynh.muzimusicapp.ui.base.dialog.ConfirmDialog
import com.ahuynh.muzimusicapp.ui.component.main.playlist.PlaylistViewModel
import com.ahuynh.muzimusicapp.ui.component.main.playlist.bottom_sheet.PlaylistModelBottomSheetArgs
import com.ahuynh.muzimusicapp.ui.component.main.playlist.bottom_sheet.PlaylistModelBottomSheetDirections
import com.ahuynh.muzimusicapp.ui.component.main.song.add_song_to_playlist_bottom_sheet.AddSongToPlaylistBottomSheet
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils.parcelable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongModelBottomSheet : BottomSheetDialogFragment(), MenuAdapter.OnItemMenuAdapterClicked {
    companion object {
        const val TAG = "SongModelBottomSheet"
    }

    private val itemMenuList = ArrayList<ItemMenu>()
    private lateinit var binding: FragmentSongModelBottomSheetBinding

    //private val viewModel by viewModels<SongViewModel>()
    private lateinit var currentSong: Song
    private val menuAdapter = MenuAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    private fun initData() {
        itemMenuList.add(
            ItemMenu(
                "Add to playlist",
                R.drawable.ic_add_to_playlist,
                ItemMenuName.PLAYLIST
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSongModelBottomSheetBinding.inflate(
            inflater,
            container,
            false
        )
        currentSong = arguments?.parcelable<Song>(Constants.SONG)!!

        handleUI()
        return binding.root
    }

    private fun handleUI() {
        binding.rcyMenu.adapter = menuAdapter
        menuAdapter.submitList(itemMenuList)

        Glide
            .with(binding.imvSong.context)
            .load(currentSong.avatar)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imvSong)
        binding.tvNameSong.text = currentSong.name


    }

    override fun onMenuClicked(menu: ItemMenu) {
        when(menu.type){
            ItemMenuName.PLAYLIST ->{
                AddSongToPlaylistBottomSheet().apply {
                    arguments = Bundle().apply {
                        putParcelable(Constants.SONG,currentSong)
                    }
                }.show(requireActivity().supportFragmentManager,null)
                dismiss()
            }


            else -> {

            }
        }
    }


}