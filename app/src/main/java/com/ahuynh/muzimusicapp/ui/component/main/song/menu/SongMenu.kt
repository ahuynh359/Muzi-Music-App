package com.ahuynh.muzimusicapp.ui.component.main.song.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.MenuAdapter
import com.ahuynh.muzimusicapp.data.model.ItemMenu
import com.ahuynh.muzimusicapp.data.model.ItemMenuName
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentSongMenuBinding
import com.ahuynh.muzimusicapp.ui.component.main.song.add_song_to_playlist_bottom_sheet.AddSongToPlaylistBottomSheet
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils.parcelable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongMenu : BottomSheetDialogFragment(), MenuAdapter.OnItemMenuAdapterClicked {
    companion object {
        const val TAG = "SongModelBottomSheet"
    }

    private val itemMenuList = ArrayList<ItemMenu>()
    private lateinit var binding: FragmentSongMenuBinding

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
        binding = FragmentSongMenuBinding.inflate(
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