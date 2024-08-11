package com.ahuynh.muzimusicapp.ui.component.user.song

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentSongBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.BaseDialogBottomSheetFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.ui.component.user.song.menu.SongMenu
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongFragment : BaseDialogBottomSheetFragment(), SongAdapter.OnSongClicked {

    companion object {
        const val TAG = "SongFragment"
    }

    private lateinit var songAdapter: SongAdapter
    private val viewModel by viewModels<SongViewModel>()
    private lateinit var songOfType: ArrayList<Song>
    private lateinit var binding: FragmentSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSongBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
        loadData()
    }

    private fun loadData() {
        viewModel.getLoveSong()
    }

    private fun observeViewModel() {
        viewModel.loveSong.observe(viewLifecycleOwner) { songs ->
            songAdapter.submitList(songs)
            songOfType = songs as ArrayList<Song>
            binding.rcySongs.visibility = if (songs.isEmpty()) View.GONE else View.VISIBLE
            binding.tvNoSongs.visibility = if (songs.isEmpty()) View.VISIBLE else View.GONE
            binding.btnPlay.visibility = if (songs.isEmpty()) View.INVISIBLE else View.VISIBLE
            binding.shimmer.stopShimmer()
            binding.shimmer.visibility = View.GONE
        }

        viewModel.des.observe(viewLifecycleOwner) { description ->
            binding.tvDes.text = description
        }
    }

    private fun setupUI() {
        songAdapter = SongAdapter(this)
        binding.rcySongs.adapter = songAdapter

        binding.btnBack.setOnClickListener {
            dismiss()
        }

        binding.btnPlay.setOnClickListener {
            startPlayerActivity(songOfType[0], songOfType)
        }
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.tvNoSongs.visibility = View.GONE
                    songAdapter.submitList(songOfType)
                } else {
                    filterSongs(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnPlay.setOnClickListener {
            startPlayerActivity(songOfType[0], songOfType)
        }
    }
    private fun filterSongs(query: String) {
        val filteredList = songOfType.filter { song ->
            song.name.contains(query, ignoreCase = true)
        }
        binding.tvNoSongs.visibility = if (filteredList.isEmpty()) View.VISIBLE else View.GONE
        binding.btnPlay.visibility = if (filteredList.isEmpty()) View.GONE else View.VISIBLE
        songAdapter.submitList(filteredList)
    }


    private fun startPlayerActivity(song: Song, songList: ArrayList<Song>) {

        Utils.sendNewMusic(requireActivity(), MusicService.ACTION_PLAY, song, songList)
    }

    override fun onSongClicked(song: Song) {
        startPlayerActivity(song, songOfType)
    }

    override fun openMenu(song: Song) {
        val fragment = SongMenu()
        fragment.arguments = Bundle().apply {
            putParcelable(Constants.SONG,song)
        }
        fragment.show(requireActivity().supportFragmentManager,null)
    }
}