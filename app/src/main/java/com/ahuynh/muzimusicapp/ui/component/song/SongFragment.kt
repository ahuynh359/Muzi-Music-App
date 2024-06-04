package com.ahuynh.muzimusicapp.ui.component.song

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.AlbumAdapter
import com.ahuynh.muzimusicapp.adapter.NewSongAdapter
import com.ahuynh.muzimusicapp.adapter.OnAlbumAdapterClicked
import com.ahuynh.muzimusicapp.adapter.TypeAdapter
import com.ahuynh.muzimusicapp.data_api.model.Album
import com.ahuynh.muzimusicapp.data_api.model.Song
import com.ahuynh.muzimusicapp.data_api.model.Type
import com.ahuynh.muzimusicapp.databinding.FragmentSongBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SongFragment : BaseFragment<FragmentSongBinding>(FragmentSongBinding::inflate),
    NewSongAdapter.OnNewSongClicked, OnAlbumAdapterClicked, TypeAdapter.OnTypeClicked {

    private val viewModel by viewModels<SongViewModel>({ requireActivity() })

    companion object {
        const val TAG = "SongFragment"
    }

    private val songAdapter = NewSongAdapter(this)
    private val albumAdapter = AlbumAdapter(this)
    private val typeAdapter = TypeAdapter(this)
    private var listSong: ArrayList<Song> = arrayListOf()
    private var listAlbum: ArrayList<Album> = arrayListOf()
    private var typeList: ArrayList<Type> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observe()
        getData()

    }

    private fun getData() {
        viewModel.getAllSongs()
        viewModel.getUnreadNoti()

    }



    private fun handleUI() {
        binding.rcySong.adapter = songAdapter
        binding.rcyAlbum.adapter = albumAdapter
        binding.rcyType.adapter = typeAdapter
        binding.swipe.setOnRefreshListener {

            getData()
        }
        binding.song.setOnClickListener(View.OnClickListener { view ->
            Toast.makeText(requireContext(), "0", Toast.LENGTH_LONG).show()
            binding.rcyAlbum.visibility = View.GONE
            binding.rcyType.visibility = View.GONE
            viewModel.songList.observe(viewLifecycleOwner) {
                binding.swipe.isRefreshing = false
                songAdapter.submitList(it)
                binding.rcySong.visibility = View.VISIBLE
                listSong = it as ArrayList<Song>
                hideShimmer()


            }

        })

        binding.album.setOnClickListener(View.OnClickListener { view ->
            Toast.makeText(requireContext(), "1", Toast.LENGTH_LONG).show()
            binding.rcySong.visibility = View.GONE
            binding.rcyType.visibility = View.GONE
            viewModel.albumList.observe(viewLifecycleOwner) {
                binding.swipe.isRefreshing = false
                albumAdapter.submitList(it)
                binding.rcyAlbum.visibility = View.VISIBLE
                listAlbum = it as ArrayList<Album>
                hideShimmer()

            }

        })

        binding.type.setOnClickListener(View.OnClickListener { view ->
            Toast.makeText(requireContext(), "2", Toast.LENGTH_LONG).show()
            binding.rcySong.visibility = View.GONE
            binding.rcyAlbum.visibility = View.GONE
            viewModel.typeList.observe(viewLifecycleOwner) {
                binding.swipe.isRefreshing = false
                typeAdapter.submitList(it)
                binding.rcyType.visibility = View.VISIBLE
                typeList = it as ArrayList<Type>
                hideShimmer()

            }

        })



    }


    private fun observe() {

        viewModel.songList.observe(viewLifecycleOwner) {
            binding.swipe.isRefreshing = false
            songAdapter.submitList(it)
            binding.rcySong.visibility = View.VISIBLE
            listSong = it as ArrayList<Song>
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
        val action = SongFragmentDirections.actionSongFragmentToDetailAlbumFragment(album)
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
        val action = SongFragmentDirections.actionSongFragmentToSongMenuBottom(song)
        findNavController().navigate(action)
    }

    override fun onTypeClicked(type: Type) {
        Toast.makeText(requireContext(), "AHIHI", Toast.LENGTH_LONG).show()
    }

}
