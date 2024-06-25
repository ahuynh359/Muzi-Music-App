package com.ahuynh.muzimusicapp.ui.component.activity.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.adapter.AlbumAdapter
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.adapter.TypeAdapter
import com.ahuynh.muzimusicapp.adapter.home.AlbumHomeAdapter
import com.ahuynh.muzimusicapp.adapter.home.SongHomeAdapter
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.databinding.FragmentHomeBinding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    AlbumHomeAdapter.OnAlbumHomeAdapterClicked, TypeAdapter.OnTypeClicked,
    SongHomeAdapter.OnSongHomeClicked {

    private val viewModel by viewModels<HomeViewModel>({ requireActivity() })

    companion object {
        const val TAG = "SongFragment"
    }

    private val newSongAdapter = SongHomeAdapter(this)
    private val newAlbumAdapter = AlbumHomeAdapter(this)
    private val typeAdapter = TypeAdapter(this)

    private var newSongList: ArrayList<Song> = arrayListOf()
    private var newAlbumList: ArrayList<Album> = arrayListOf()
    private var typeList: ArrayList<Type> = arrayListOf()
    private var loveList: ArrayList<Song> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observe()


    }

    private fun observe() {

        handleTypeList()
        handleNewSongList()
        handleNewAlbumList()


    }

    //Type List
    private fun handleTypeList() {
        binding.rcyType.adapter = typeAdapter
        viewModel.typeList.observe(viewLifecycleOwner) {
            binding.rcyType.visibility = View.VISIBLE
            if (it != null) {
                typeList = it as ArrayList<Type>
                typeAdapter.submitList(it)
            }
            binding.shimmerType.stopShimmer()
            binding.shimmerType.visibility = View.INVISIBLE


        }
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


    private fun handleUI() {


    }

    override fun onSongClicked(song: Song) {

    }


    override fun onAlbumClicked(album: Album) {

    }


    override fun onTypeClicked(type: Type) {

    }


}
