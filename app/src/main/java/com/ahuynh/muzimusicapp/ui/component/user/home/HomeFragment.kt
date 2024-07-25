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
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.AlbumAdapter
import com.ahuynh.muzimusicapp.adapter.AlbumViewType
import com.ahuynh.muzimusicapp.adapter.SingerAdapter
import com.ahuynh.muzimusicapp.adapter.SingerViewType
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.adapter.SongEntityAdapter
import com.ahuynh.muzimusicapp.adapter.TypeAdapter
import com.ahuynh.muzimusicapp.adapter.TypeViewType
import com.ahuynh.muzimusicapp.adapter.VerticalSongAdapter
import com.ahuynh.muzimusicapp.data.database.entity.SongEntity
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.Type
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
    AlbumAdapter.OnAlbumClicked,
    SongAdapter.OnSongClicked, SingerAdapter.OnSingerClicked,
    SongEntityAdapter.OnSongEntityClick, TypeAdapter.OnTypeClicked {

    private val viewModel by viewModels<HomeViewModel>({ requireActivity() })

    private var scrollPosition = 0

    companion object {
        const val TAG = "HomeFragment"
        const val SCROLL_POSITION_KEY = "scroll_position_key"
    }

    private val songEntityAdapter = SongEntityAdapter(this)
    private lateinit var newSongAdapter: VerticalSongAdapter
    private val newAlbumAdapter = AlbumAdapter(this, AlbumViewType.HOME)
    private val newTypeAdapter = TypeAdapter(this, TypeViewType.HOME)
    private val newSingerAdapter = SingerAdapter(this,SingerViewType.HOME)

    private var newSongList: ArrayList<Song> = arrayListOf()
    private var newTypeList: ArrayList<Type> = arrayListOf()
    private var newAlbumList: ArrayList<Album> = arrayListOf()
    private var newSingerList: ArrayList<Singer> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        savedInstanceState?.let {
            scrollPosition = it.getInt(SCROLL_POSITION_KEY, 0)
        }
        return super.onCreateView(inflater, container, savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
        observe()

    }

    override fun onResume() {
        super.onResume()
        binding.scrollView.post {
            binding.scrollView.scrollTo(0, scrollPosition)
        }
        getData()
    }

    override fun onPause() {
        super.onPause()
        scrollPosition = binding.scrollView.scrollY
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SCROLL_POSITION_KEY, scrollPosition)
    }


    private fun getData() {
        viewModel.getNewSongs()
        viewModel.getNewAlbums()
        viewModel.getNewSingers()
        viewModel.getRecentSongs()
        viewModel.getNewTypes()


    }

    private fun observe() {

        handleRecentSong()
        handleNewSongList()
        handleNewAlbumList()
        handleNewSingerList()
        handleNewTypeList()


    }

    private fun handleNewTypeList() {
        binding.rcyNewType.adapter = newTypeAdapter
        viewModel.newTypeList.observe(viewLifecycleOwner) {
            Log.d("ABC", it.toString())
            binding.rcyNewType.visibility = View.VISIBLE
            if (it != null) {
                newTypeList = it as ArrayList<Type>
                newTypeAdapter.submitList(it)
            }
            binding.shimmerNewType.stopShimmer()
            binding.shimmerNewType.visibility = View.INVISIBLE


        }
    }

    private fun handleRecentSong() {
        binding.rcyRecentSongs.adapter = songEntityAdapter
        viewModel.recentSong.observe(viewLifecycleOwner) {
            binding.rcyRecentSongs.visibility = View.VISIBLE
            songEntityAdapter.submitList(it)
            binding.shimmerRecentSongs.stopShimmer()
            binding.shimmerRecentSongs.visibility = View.INVISIBLE


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


    private fun handleUI() {

        val snapHelper1 = LinearSnapHelper()
        snapHelper1.attachToRecyclerView(binding.rcyNewSong)

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

    override fun onMoreItemAlbumClicked(album: Album) {

    }


    override fun onSingerClicked(singer: Singer) {
        val action = HomeFragmentDirections.actionHomeFragmentToSingerDetailFragment(singer)
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

    override fun onTypeClicked(type: Type) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailTypeFragment(type)
        findNavController().navigate(action)
    }

    override fun onMoreClicked(type: Type) {
    }


}



