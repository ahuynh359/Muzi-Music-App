package com.ahuynh.muzimusicapp.ui.component.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.AlbumAdapter
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.adapter.TypeAdapter
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.databinding.FragmentHomeBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    SongAdapter.OnNewSongClicked, AlbumAdapter.OnAlbumAdapterClicked, TypeAdapter.OnTypeClicked {

    private val viewModel by viewModels<HomeViewModel>({ requireActivity() })

    companion object {
        const val TAG = "SongFragment"
    }

    private val songAdapter = SongAdapter(this)
    private val loveAdapter = SongAdapter(this)
    private val albumAdapter = AlbumAdapter(this)
    private val typeAdapter = TypeAdapter(this)
    private var listSong: ArrayList<Song> = arrayListOf()
    private var listAlbum: ArrayList<Album> = arrayListOf()
    private var typeList: ArrayList<Type> = arrayListOf()
    private var loveList: ArrayList<Song> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       
        handleUI()
        //observe()
        //getData()

    }

    private fun getData() {
        viewModel.getAllSongs()
        viewModel.getAllAlbum()
        viewModel.getAllType()
        viewModel.getUnreadNoti()

    }

    override fun onResume() {
        super.onResume()
        binding.rcyAlbum.visibility = View.GONE
        binding.rcyType.visibility = View.GONE
        binding.rcyLove.visibility = View.GONE
        viewModel.songList.observe(viewLifecycleOwner) {
            binding.swipe.isRefreshing = false
            binding.rcySong.visibility = View.VISIBLE
            if (it != null) {
                listSong = it as ArrayList<Song>
                songAdapter.submitList(it)
            }
            hideShimmer()


        }
        val defaultChipId = binding.chipGroup.getChildAt(0)?.id
        if (defaultChipId != null) {
            binding.chipGroup.check(defaultChipId)
        }
    }


    private fun handleUI() {


        binding.rcySong.adapter = songAdapter
        binding.rcyAlbum.adapter = albumAdapter
        binding.rcyType.adapter = typeAdapter
        binding.rcyLove.adapter = loveAdapter

        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedChip = group.checkedChipId

            when (selectedChip) {
                R.id.song -> {
                    binding.swipe.setOnRefreshListener {

                        viewModel.getAllSongs()
                    }

                    binding.rcyAlbum.visibility = View.GONE
                    binding.rcyType.visibility = View.GONE
                    binding.rcyLove.visibility = View.GONE
                    viewModel.songList.observe(viewLifecycleOwner) {
                        binding.swipe.isRefreshing = false
                        binding.rcySong.visibility = View.VISIBLE
                        if (it != null) {
                            listSong = it as ArrayList<Song>
                            songAdapter.submitList(it)
                        }
                        hideShimmer()


                    }
                }

                R.id.album -> {
                    binding.swipe.setOnRefreshListener {

                        viewModel.getAllAlbum()
                    }
                    binding.rcySong.visibility = View.GONE
                    binding.rcyType.visibility = View.GONE
                    binding.rcyLove.visibility = View.GONE
                    viewModel.albumList.observe(viewLifecycleOwner) {
                        binding.swipe.isRefreshing = false
                        albumAdapter.submitList(it)

                        binding.rcyAlbum.visibility = View.VISIBLE
                        listAlbum = it as ArrayList<Album>
                        hideShimmer()

                    }

                }

                R.id.type -> {
                    binding.swipe.setOnRefreshListener {

                        viewModel.getAllType()
                    }
                    binding.rcySong.visibility = View.GONE
                    binding.rcyAlbum.visibility = View.GONE
                    binding.rcyLove.visibility = View.GONE
                    viewModel.typeList.observe(viewLifecycleOwner) {
                        binding.swipe.isRefreshing = false
                        typeAdapter.submitList(it)
                        binding.rcyType.visibility = View.VISIBLE
                        typeList = it as ArrayList<Type>
                        hideShimmer()

                    }
                }

                R.id.love -> {
                    binding.swipe.setOnRefreshListener {

                        viewModel.getAllLoveSong()
                    }
                    binding.rcySong.visibility = View.GONE
                    binding.rcyAlbum.visibility = View.GONE
                    binding.rcyType.visibility = View.GONE
                    viewModel.loveSongList.observe(viewLifecycleOwner) {
                        binding.swipe.isRefreshing = false
                        loveAdapter.submitList(it)
                        binding.rcyLove.visibility = View.VISIBLE
                        loveList = it as ArrayList<Song>
                        hideShimmer()

                    }
                }
            }
        }



    }


    private fun observe() {

        viewModel.songList.observe(viewLifecycleOwner) {
            binding.swipe.isRefreshing = false

            binding.rcySong.visibility = View.VISIBLE
            if (it != null) {
                listSong = it as ArrayList<Song>
                songAdapter.submitList(it)
            }
            hideShimmer()


        }


    }

    private fun hideShimmer() {
        binding.shimmerSong.stopShimmer()
        binding.shimmerSong.visibility = View.GONE
    }

    private fun showShimmer() {
        binding.shimmerSong.startShimmer()
        binding.shimmerSong.visibility = View.VISIBLE
    }


    override fun onAlbumClicked(album: Album) {
        val action = HomeFragmentDirections.actionSongFragmentToDetailAlbumFragment(album)
        findNavController().navigate(action)

    }

    override fun onMoreItemAlbumClicked(album: Album) {
        Toast.makeText(requireContext(), "AHIHI", Toast.LENGTH_LONG).show()
    }

    override fun onSongClicked(song: Song) {
        startActivity(Intent(requireContext(), PlayerActivity::class.java))
        Utils.sendNewMusic(
            requireContext(),
            MusicService.ACTION_PLAY,
            song, listSong
        )

    }

    override fun openMenu(song: Song) {
        val action = HomeFragmentDirections.actionSongFragmentToSongMenuBottom(song)
        findNavController().navigate(action)
    }

    override fun onTypeClicked(type: Type) {
        val action = HomeFragmentDirections.actionSongFragmentToDetailTypeFragment(type)
        findNavController().navigate(action)
    }

}
