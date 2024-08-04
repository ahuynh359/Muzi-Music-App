package com.ahuynh.muzimusicapp.ui.component.user.song.menu
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.MenuAdapter
import com.ahuynh.muzimusicapp.data.model.ItemMenu
import com.ahuynh.muzimusicapp.data.model.ItemMenuName
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentSongMenuBinding
import com.ahuynh.muzimusicapp.ui.component.user.song.add_song_to_playlist.AddSongToPlaylistFragment
import com.ahuynh.muzimusicapp.ui.component.user.song.menu.SongMenuViewModel
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils.loadImage
import com.ahuynh.muzimusicapp.utils.Utils.parcelable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongMenu : BottomSheetDialogFragment(), MenuAdapter.OnItemMenuAdapterClicked {
    companion object {
        const val TAG = "SongMenu"
    }

    private val itemMenuList = ArrayList<ItemMenu>()
    private lateinit var binding: FragmentSongMenuBinding
    private val viewModel by viewModels<SongMenuViewModel>()
    private lateinit var currentSong: Song
    private val menuAdapter = MenuAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val song: Song? = arguments?.parcelable(Constants.SONG)
        if (song == null) dismiss()
        else currentSong = song
    }

    override fun onResume() {
        super.onResume()
        viewModel.isUserLoveSong(currentSong.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSongMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.rcyMenu.adapter = menuAdapter
        binding.tvSinger.text = currentSong.singers.joinToString(", ") { it.name }
        binding.imvSong.loadImage(currentSong.avatar)
        binding.tvNameSong.text = currentSong.name
    }

    private fun initData(isLoved: Boolean) {
        itemMenuList.clear()
        itemMenuList.add(
            ItemMenu(
                "Add to playlist",
                R.drawable.ic_add_to_playlist,
                ItemMenuName.PLAYLIST
            )
        )
        if (isLoved) {
            itemMenuList.add(
                ItemMenu(
                    "Unlove song",
                    R.drawable.ic_hearted,
                    ItemMenuName.LOVE
                )
            )
        } else {
            itemMenuList.add(
                ItemMenu(
                    "Love song",
                    R.drawable.ic_heart_small,
                    ItemMenuName.LOVE
                )
            )
        }
        menuAdapter.submitList(itemMenuList.toList())
    }

    private fun observeViewModel() {
        viewModel.loveSong.observe(viewLifecycleOwner) { isLoved ->
            initData(isLoved)
        }
    }

    override fun onMenuClicked(menu: ItemMenu) {
        when (menu.type) {
            ItemMenuName.PLAYLIST -> {
                val fragment = AddSongToPlaylistFragment()
                fragment.arguments = Bundle().apply {
                    putParcelable(Constants.SONG, currentSong)
                }
                fragment.show(requireActivity().supportFragmentManager, null)
                dismiss()
            }

            ItemMenuName.LOVE -> {
                viewModel.loveOrUnlove(currentSong.id)
                dismiss()
            }

            else -> {
            }
        }
    }
}