package com.ahuynh.muzimusicapp.ui.component.user.album.detail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentDetailAlbumBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.user.album.AlbumViewModel
import com.ahuynh.muzimusicapp.ui.component.user.song.menu.SongMenu
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils
import com.ahuynh.muzimusicapp.utils.Utils.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailAlbumFragment :
    BaseFragment<FragmentDetailAlbumBinding>(FragmentDetailAlbumBinding::inflate),
    SongAdapter.OnSongClicked {

    companion object {
        const val TAG = "DetailAlbumFragment"
    }

    private val songAdapter = SongAdapter(this)
    private val viewModel by viewModels<AlbumViewModel>()
    private lateinit var songOfAlbum: ArrayList<Song>
    private lateinit var currentAlbum: Album

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentAlbum = DetailAlbumFragmentArgs.fromBundle(requireArguments()).album


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleUI()
        observe()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        viewModel.getSongOfAlbum(currentAlbum.id)
    }

    private fun observe() {

        viewModel.songOfAlbumList.observe(viewLifecycleOwner) { songs ->
            songAdapter.submitList(songs)
            songOfAlbum = ArrayList(songs)
            binding.rcySongs.visibility = if (songs.isEmpty()) View.GONE else View.VISIBLE
            binding.tvNoSongs.visibility = if (songs.isEmpty()) View.VISIBLE else View.GONE
            binding.shimmer.stopShimmer()
            binding.shimmer.visibility = View.GONE
        }

    }

    private fun handleUI() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvAlbumName.text = currentAlbum?.name
        binding.rcySongs.adapter = songAdapter
        binding.imvAlbum.loadImage(currentAlbum.avatar)
        binding.topAppBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val totalScrollRange = appBarLayout.totalScrollRange
            if (totalScrollRange + verticalOffset == 0) {
                binding.edtSearch.visibility = View.VISIBLE
            } else {
                binding.edtSearch.visibility = View.GONE
            }
        }
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.tvNoSongs.visibility =
                        if (songOfAlbum.isEmpty()) View.VISIBLE else View.GONE
                    songAdapter.submitList(songOfAlbum)
                } else {
                    filterSongs(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })


    }

    private fun filterSongs(query: String) {
        val filteredList = songOfAlbum.filter { song ->
            song.name.contains(query, ignoreCase = true)
        }
        binding.tvNoSongs.visibility =
            if (filteredList.isEmpty() || songOfAlbum.isEmpty()) View.VISIBLE else View.GONE
        songAdapter.submitList(filteredList)
    }


    override fun onSongClicked(song: Song) {
        Utils.sendNewMusic(
            requireActivity(),
            MusicService.ACTION_PLAY,
            song, songOfAlbum
        )
    }

    override fun openMenu(song: Song) {
        val fragment = SongMenu()
        fragment.arguments = Bundle().apply {
            putParcelable(Constants.SONG, song)
        }
        fragment.show(requireActivity().supportFragmentManager, null)
    }


}