package com.ahuynh.muzimusicapp.ui.component.user.type.detail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.databinding.FragmentDetailTypeBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.user.song.menu.SongMenu
import com.ahuynh.muzimusicapp.ui.component.user.type.TypeViewModel
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils
import com.ahuynh.muzimusicapp.utils.Utils.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTypeFragment :  BaseFragment<FragmentDetailTypeBinding>(FragmentDetailTypeBinding::inflate), SongAdapter.OnSongClicked {

    companion object {
        const val TAG = "DetailTypeFragment"
    }

    private val songAdapter = SongAdapter(this)
    private val viewModel by viewModels<TypeViewModel>()
    private lateinit var songOfType: ArrayList<Song>
    private lateinit var currentType: Type

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentType = DetailTypeFragmentArgs.fromBundle(requireArguments()).type
    }


    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
    }

    private fun getData() {
        viewModel.getSongOfType(currentType.id)
    }

    private fun observeViewModel() {
        viewModel.songOfType.observe(viewLifecycleOwner) { songs ->
            songAdapter.submitList(songs)
            songOfType = ArrayList(songs)
            binding.rcySongs.visibility = if (songs.isEmpty()) View.GONE else View.VISIBLE
            binding.tvNoSongs.visibility = if (songs.isEmpty()) View.VISIBLE else View.GONE
            binding.btnPlay.visibility = if (songs.isEmpty()) View.INVISIBLE else View.VISIBLE
            binding.shimmer.stopShimmer()
            binding.shimmer.visibility = View.GONE
        }
    }

    private fun setupUI() {
        binding.rcySongs.adapter = songAdapter
        binding.imvType.loadImage(currentType.avatar)
        binding.tvTypeName.text = currentType.name
        binding.tvTypeName1.text = currentType.name
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
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
        binding.tvNoSongs.visibility = if (filteredList.isEmpty() || songOfType.isEmpty()) View.VISIBLE else View.GONE
        binding.btnPlay.visibility = if (filteredList.isEmpty()) View.INVISIBLE else View.VISIBLE
        songAdapter.submitList(filteredList)
    }

    private fun startPlayerActivity(song: Song, songList: ArrayList<Song>) {
        // startActivity(Intent(requireContext(), PlayerActivity::class.java))
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