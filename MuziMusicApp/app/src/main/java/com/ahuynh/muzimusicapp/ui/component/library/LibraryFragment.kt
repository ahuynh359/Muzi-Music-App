package com.ahuynh.muzimusicapp.ui.component.library

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.databinding.FragmentLibraryBinding
import com.ahuynh.muzimusicapp.model.Playlist
import com.ahuynh.muzimusicapp.model.Song
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Constants.ACTION_PLAY
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.utils.Utils
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LibraryFragment : BaseFragment<FragmentLibraryBinding>(FragmentLibraryBinding::inflate) {

    private val viewModel by viewModels<LibraryViewModel>()
    private lateinit var songAdapter: SongAdapter
    private lateinit var playlistAdapter: PlaylistAdapter
    private lateinit var snackbar: Snackbar

    private var listSong: ArrayList<Song> = arrayListOf()
    private var listPlaylist: ArrayList<Playlist> = arrayListOf()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        snackbar = Snackbar.make(
            binding.swipeRefresh,
            "No Internet Connection",
            Snackbar.LENGTH_INDEFINITE
        ).setAction("Wifi") {
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }
        observers()
        songAdapter = SongAdapter(object : OnSongClicked {
            override fun onSongClicked(song: Song) {
                Toast.makeText(requireContext(), song.name, Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), PlayerActivity::class.java))
                Utils.sendMusic(
                    requireContext(),
                    ACTION_PLAY,
                    song,
                    songList = listSong
                )
            }

        })

        playlistAdapter = PlaylistAdapter(object : OnPlaylistClicked {
            override fun onPlaylistClicked(playlist: Playlist) {
                Toast.makeText(activity, playlist.name, Toast.LENGTH_SHORT).show()
                val list: List<Song> = getSongsByIds(listSong, playlist.songs!!)
                val action =
                    LibraryFragmentDirections.actionLibraryFragmentToPlaylistFragment(
                        playlist,
                        list.toTypedArray()
                    )
                findNavController().navigate(action)
                Log.d("LibraryFragment", list.toString())
            }

        })
        binding.rcySong.adapter = songAdapter
        binding.rcyPlaylist.adapter = playlistAdapter
        fetchData()

        binding.swipeRefresh.setOnRefreshListener {
            fetchData()
        }

        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedId ->
            checkedId.forEach { id ->
                val chip: Chip? = group.findViewById(id)
                chip?.let {
                    val selectedChipText = it.text
                    when (selectedChipText) {
                        resources.getString(R.string.songs) -> {
                            binding.rcySong.visibility = View.VISIBLE
                            binding.rcyPlaylist.visibility = View.GONE
                            //Observe songs
                            viewModel.song.observe(viewLifecycleOwner) { response ->
                                when (response) {
                                    is Response.Loading -> {
                                    }

                                    is Response.Success -> {
                                        binding.swipeRefresh.isRefreshing = false
                                        songAdapter.submitList(response.data)
                                        listSong = response.data as ArrayList<Song>
                                        binding.shimmerSong.stopShimmer()
                                        binding.rcySong.visibility = View.VISIBLE
                                        binding.shimmerSong.visibility = View.GONE
                                    }

                                    is Response.Failure -> {

                                    }
                                }
                            }
                        }

                        resources.getString(R.string.albums) -> {

                        }

                        resources.getString(R.string.playlists) -> {
                            binding.rcySong.visibility = View.GONE
                            binding.rcyPlaylist.visibility = View.VISIBLE
                            //observe playlist
                            viewModel.playlists.observe(viewLifecycleOwner) { response ->
                                when (response) {
                                    is Response.Loading -> {
                                    }

                                    is Response.Success -> {
                                        binding.swipeRefresh.isRefreshing = false
                                        playlistAdapter.submitList(response.data)
                                        listPlaylist = response.data as ArrayList<Playlist>
                                        binding.shimmerSong.stopShimmer()
                                        binding.rcyPlaylist.visibility = View.VISIBLE
                                        binding.shimmerSong.visibility = View.GONE
                                    }

                                    is Response.Failure -> {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


    }

    fun getSongsByIds(songList: ArrayList<Song>, idList: ArrayList<String>): List<Song> {
        return songList.filter { it.id in idList }
    }


    private fun fetchData() {
        viewModel.getSong()
        viewModel.getPlaylist()
    }


    private fun observers() {
        //Observe network change
        networkConnectivityObserver.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    if (snackbar.isShown) {
                        snackbar.dismiss()
                    }
                }

                else -> {
                    snackbar.show()
                }
            }
        }
//        val chip = binding.chipGroup.getChildAt(0) as Chip
//        if (chip.isChecked) {
//            viewModel.song.observe(viewLifecycleOwner) { response ->
//                when (response) {
//                    is Response.Loading -> {
//                    }
//
//                    is Response.Success -> {
//                        binding.swipeRefresh.isRefreshing = false
//                        songAdapter.submitList(response.data)
//                        listSong = response.data as ArrayList<Song>
//                        binding.shimmerSong.stopShimmer()
//                        binding.rcySong.visibility = View.VISIBLE
//                        binding.shimmerSong.visibility = View.GONE
//                    }
//
//                    is Response.Failure -> {
//                    }
//                }
//            }
//        }
//        else
//            binding.rcySong.visibility = View.GONE



    }


}