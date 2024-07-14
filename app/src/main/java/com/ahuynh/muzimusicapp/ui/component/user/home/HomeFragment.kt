package com.ahuynh.muzimusicapp.ui.component.user.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.adapter.SongEntityAdapter
import com.ahuynh.muzimusicapp.adapter.VerticalSongAdapter
import com.ahuynh.muzimusicapp.adapter.home.AlbumHomeAdapter
import com.ahuynh.muzimusicapp.adapter.home.SingerHomeAdapter
import com.ahuynh.muzimusicapp.adapter.home.SongHomeAdapter
import com.ahuynh.muzimusicapp.data.database.entity.SongEntity
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentHomeBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.user.song.menu.SongMenu
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.min


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    AlbumHomeAdapter.OnAlbumHomeAdapterClicked,
    SongHomeAdapter.OnSongHomeClick, SingerHomeAdapter.OnSingerHomeClicked,
    SongEntityAdapter.OnSongEntityClick{

    private val viewModel by viewModels<HomeViewModel>({ requireActivity() })

    companion object {
        const val TAG = "SongFragment"
    }

    private val songEntityAdapter = SongEntityAdapter(this)
    private lateinit var newSongAdapter: VerticalSongAdapter
    private lateinit var topSongAdapter: VerticalSongAdapter
    private val newAlbumAdapter = AlbumHomeAdapter(this)
    private val newSingerAdapter = SingerHomeAdapter(this)

    private var newSongList: ArrayList<Song> = arrayListOf()
    private var newAlbumList: ArrayList<Album> = arrayListOf()
    private var newSingerList: ArrayList<Singer> = arrayListOf()
    private var topSongList: ArrayList<Song> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ABC", "On create")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("ABC", "On create view")
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("ABC", "On view created")
        handleUI()
        observe()


    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("ABC", "onViewStateRestored")
    }

    override fun onStart() {
        super.onStart()
        Log.d("ABC", "onStart")
    }

    override fun onResume() {
        super.onResume()
        getData()
        Log.d("ABC", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ABC", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ABC", "onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("ABC", "onSaveInstanceState")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("ABC", "onSaveInstanceState")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ABC", "onDestroy")
    }

    private fun getData() {
        viewModel.getNewSongs()
        viewModel.getNewAlbums()
        viewModel.getNewSingers()
        viewModel.getRecentSongs()
        viewModel.getTopSongs()

    }

    private fun observe() {

        handleRecentSong()
        handleNewSongList()
        handleNewAlbumList()
        handleNewSingerList()
        handleTopSongList()


    }

    private fun handleRecentSong() {
        binding.rcyRecentSongs.adapter = songEntityAdapter
        viewModel.recentSong.observe(viewLifecycleOwner) {

            if (it.isNotEmpty()) {
                binding.rcyRecentSongs.visibility = View.VISIBLE
                //newAlbumList = it as ArrayList<Album>
                songEntityAdapter.submitList(it)
                binding.shimmerRecentSongs.stopShimmer()
                binding.shimmerRecentSongs.visibility = View.INVISIBLE

            } else {
                binding.rcyRecentSongs.visibility = View.GONE
            }


        }
    }


    private fun handleNewSongList() {
        viewModel.newSongList.observe(viewLifecycleOwner) {
            binding.rcyNewSong.visibility = View.VISIBLE
            val list = it.subList(0, min(9, it.size))
            newSongList = it as ArrayList<Song>
            val songLists = list.chunked(3)

            newSongAdapter = VerticalSongAdapter(songLists, this)
            binding.rcyNewSong.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rcyNewSong.adapter = newSongAdapter

            binding.shimmerNewSong.stopShimmer()
            binding.shimmerNewSong.visibility = View.INVISIBLE


        }
    }

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


    private fun handleTopSongList() {
        viewModel.topSongList.observe(viewLifecycleOwner) {
            binding.rcyTopSong.visibility = View.VISIBLE
            val list = it.subList(0, min(9, it.size))
            topSongList = it as ArrayList<Song>
            val songLists = list.chunked(3)

            topSongAdapter = VerticalSongAdapter(songLists, this)
            binding.rcyTopSong.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rcyTopSong.adapter = topSongAdapter

            binding.shimmerTopSong.stopShimmer()
            binding.shimmerTopSong.visibility = View.INVISIBLE


        }
    }



    private fun handleUI() {

        val snapHelper1 = LinearSnapHelper()
        snapHelper1.attachToRecyclerView(binding.rcyNewSong)

        binding.tvClear.setOnClickListener {
            viewModel.clearRecentSongs()
        }
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

    override fun openMenu(song: Song) {
        SongMenu().apply {
            arguments = Bundle().apply {
                putParcelable(Constants.SONG, song)
            }
        }.show(requireActivity().supportFragmentManager, null)
    }


    override fun onAlbumClicked(album: Album) {

    }


    override fun onSingerClicked(singer: Singer) {
        val action = HomeFragmentDirections.actionHomeFragmentToSingerFragment(singer)
        findNavController().navigate(action)
    }

    override fun onSongEntityClick(songEntity: SongEntity) {
        val song = songEntity.toSong()

        startActivity(Intent(context, PlayerActivity::class.java))
        Utils.sendMusic(
            requireContext(),
            MusicService.ACTION_PLAY,
            song,
            arrayListOf(song)
        )
    }


}



