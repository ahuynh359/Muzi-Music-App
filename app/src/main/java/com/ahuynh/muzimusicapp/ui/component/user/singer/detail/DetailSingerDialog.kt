package com.ahuynh.muzimusicapp.ui.component.user.singer.detail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentDetailSingerBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.BaseDialogBottomSheetFragment
import com.ahuynh.muzimusicapp.ui.component.user.singer.SingerViewModel
import com.ahuynh.muzimusicapp.ui.component.user.song.menu.SongMenu
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils
import com.ahuynh.muzimusicapp.utils.Utils.loadImage
import com.ahuynh.muzimusicapp.utils.Utils.parcelable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailSingerDialog :
    BaseDialogBottomSheetFragment(),
    SongAdapter.OnSongClicked {

    companion object {
        const val TAG = "DetailSingerFragment"
    }

    private val songAdapter = SongAdapter(this)
    private val viewModel by viewModels<SingerViewModel>({ requireActivity() })
    private var songOfSinger: ArrayList<Song> = arrayListOf()
    private lateinit var currentSinger: Singer
    private lateinit var binding: FragmentDetailSingerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val singer = arguments?.parcelable<Singer>(Constants.SINGER)
        if (singer != null)
            currentSinger = singer
        else dismiss()


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailSingerBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
        observe()

    }

    private fun getData() {
        viewModel.getSongOfSinger(currentSinger.id)
        viewModel.isUserLoveSinger(currentSinger.id)
    }

    private fun observe() {
        binding.apply {
            imvSinger.loadImage(currentSinger.avatar)
            topAppBar.title = currentSinger.name
            tvDescription.originalText =
                if (currentSinger.description == null) getString(R.string.no_description) else currentSinger.description.toString()
        }

        viewModel.songOfSinger.observe(viewLifecycleOwner) {
            songAdapter.submitList(it)
            songOfSinger = it as ArrayList<Song>
            binding.rcySongs.visibility = View.VISIBLE
            if (it.isEmpty()) {
                binding.tvNoSongs.visibility = View.VISIBLE
            } else {
                binding.tvNoSongs.visibility = View.GONE
            }
            binding.shimmer.stopShimmer()
            binding.shimmer.visibility = View.GONE
        }

        viewModel.loveSinger.observe(viewLifecycleOwner) {
            if (it) {
                binding.btnFollow.text = getString(R.string.unfollow)
                binding.btnFollow.setBackgroundResource(R.drawable.btn_transparent)
            } else {
                binding.btnFollow.text = getString(R.string.follow)
                binding.btnFollow.setBackgroundResource(R.drawable.btn_transparent)
            }
        }


    }


    private fun handleUI() {
        binding.toolbar.setNavigationOnClickListener {
            dismiss()
        }
        binding.apply {
            rcySongs.adapter = songAdapter

        }

        binding.topAppBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val totalScrollRange = appBarLayout.totalScrollRange
            if (totalScrollRange + verticalOffset == 0) {
                binding.edtSearch.visibility = View.VISIBLE
            } else {
                binding.edtSearch.visibility = View.GONE
            }
        }

        binding.btnFollow.setOnClickListener {
            viewModel.loveOrUnloveSinger(currentSinger.id)
        }

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.tvNoSongs.visibility =
                        if (songOfSinger.isEmpty()) View.VISIBLE else View.GONE
                    songAdapter.submitList(songOfSinger)
                } else {
                    filterSongs(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })


    }

    private fun filterSongs(query: String) {
        val filteredList = if (query.isEmpty()) {
            songOfSinger
        } else {
            songOfSinger.filter { song ->
                song.name.contains(query, ignoreCase = true)
            }
        }
        binding.tvNoSongs.visibility = if (filteredList.isEmpty()) View.VISIBLE else View.GONE
        songAdapter.submitList(filteredList)
    }


    override fun onSongClicked(song: Song) {
        Utils.sendNewMusic(
            requireActivity(),
            MusicService.ACTION_PLAY,
            song, songOfSinger
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