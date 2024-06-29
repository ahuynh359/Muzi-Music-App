package com.ahuynh.muzimusicapp.ui.component.main.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.adapter.home.AlbumHomeAdapter
import com.ahuynh.muzimusicapp.adapter.home.SingerHomeAdapter
import com.ahuynh.muzimusicapp.adapter.home.SongHomeAdapter
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentHomeBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    AlbumHomeAdapter.OnAlbumHomeAdapterClicked,
    SongHomeAdapter.OnSongHomeClicked, SingerHomeAdapter.OnSingerHomeClicked {

    private val viewModel by viewModels<HomeViewModel>({ requireActivity() })

    companion object {
        const val TAG = "SongFragment"
    }

    private val newSongAdapter = SongHomeAdapter(this)
    private val newAlbumAdapter = AlbumHomeAdapter(this)
    private val newSingerAdapter = SingerHomeAdapter(this)

    private var newSongList: ArrayList<Song> = arrayListOf()
    private var newAlbumList: ArrayList<Album> = arrayListOf()
    private var newSingerList: ArrayList<Singer> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observe()


    }

    private fun observe() {


        handleNewSongList()
        handleNewAlbumList()
        handleNewSingerList()


    }



    //New Song List
    private fun handleNewSongList() {
        binding.rcyNewSong.adapter = newSongAdapter
        viewModel.newSongList.observe(viewLifecycleOwner) {
            binding.rcyNewSong.visibility = View.VISIBLE
            if (it != null) {
                newSongList = it as ArrayList<Song>
                newSongAdapter.submitList(it)
            }
            binding.shimmerNewSong.stopShimmer()
            binding.shimmerNewSong.visibility = View.INVISIBLE


        }
    }

    //New Song List
    private fun handleNewAlbumList() {
        binding.rcyNewAlbum.adapter = newAlbumAdapter
        viewModel.newAlbumList.observe(viewLifecycleOwner) {
            binding.rcyNewAlbum.visibility = View.VISIBLE
            if (it != null) {
                newAlbumList = it as ArrayList<Album>
                newAlbumAdapter.submitList(it)
            }
            binding.shimmerNewAlbum.stopShimmer()
            binding.shimmerNewAlbum.visibility = View.INVISIBLE


        }
    }

    //New Singer List
    private fun handleNewSingerList() {
        binding.rcyNewSinger.adapter = newSingerAdapter
        viewModel.newSingerList.observe(viewLifecycleOwner) {
            binding.rcyNewSinger.visibility = View.VISIBLE
            if (it != null) {
                newSingerList = it as ArrayList<Singer>
                newSingerAdapter.submitList(it)
            }
            binding.shimmerNewSinger.stopShimmer()
            binding.shimmerNewSinger.visibility = View.INVISIBLE


        }
    }



    private fun handleUI() {


    }

    override fun onSongClicked(song: Song) {
        startActivity(Intent(context, PlayerActivity::class.java))
        Utils.sendMusic(
            requireContext(),
            MusicService.ACTION_PLAY,
            song,
            newSongList
        )
    }


    override fun onAlbumClicked(album: Album) {

    }



    override fun onSingerClicked(singer: Singer) {

    }




}
