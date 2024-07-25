package com.ahuynh.muzimusicapp.ui.component.admin.song.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.adapter.UserAdapter
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.databinding.FragmentSearchManageSongBinding
import com.ahuynh.muzimusicapp.databinding.FragmentSearchManageUserBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.song.ManageSongViewModel
import com.ahuynh.muzimusicapp.ui.component.admin.user.ManageUserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchManageSongFragment : BaseFragment<FragmentSearchManageSongBinding>(
    FragmentSearchManageSongBinding::inflate) ,
    SongAdapter.OnSongClicked{

    private val viewModel by viewModels<ManageSongViewModel>({ requireActivity() })
    private val songAdapter = SongAdapter(this)

    private var songList: ArrayList<Song> = arrayListOf()


    companion object {
        const val TAG = "SearchManageSongFragment"
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllSongs()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observeData()


    }

    private fun handleUI() {
        binding.rcySong.adapter = songAdapter
        binding.edtSearch.clearFocus()
        binding.edtSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    songAdapter.submitList(songList)
                    binding.tvNoSong.visibility = View.INVISIBLE

                } else
                    performSearch(newText)
                return false
            }
        })

        binding.tvCancle.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun performSearch(query: String) {
        val searchSongList = mutableListOf<Song>()
        for (s in songList) {
            if (s.name.lowercase().contains(query.lowercase())
            ) {
                searchSongList.add(s)
            }
        }
        if (searchSongList.isEmpty()) {
            songAdapter.submitList(arrayListOf())
            binding.tvNoSong.visibility = View.VISIBLE
        } else {
            binding.tvNoSong.visibility = View.INVISIBLE
            songAdapter.submitList(searchSongList)
        }
    }

    private fun observeData() {
        viewModel.songList.observe(viewLifecycleOwner) {
            binding.rcySong.visibility = View.VISIBLE
            if (it != null) {
                songList = it as ArrayList<Song>
                songAdapter.submitList(it)
            }


        }

    }

    override fun onSongClicked(song: Song) {

    }

    override fun openMenu(song: Song) {
    }


}
