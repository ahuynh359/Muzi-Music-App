package com.ahuynh.muzimusicapp.ui.component.admin.song

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.adapter.UserAdapter
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.databinding.FragmentManageSongBinding
import com.ahuynh.muzimusicapp.databinding.FragmentManageUserBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.user.ManageUserFragmentDirections
import com.ahuynh.muzimusicapp.ui.component.admin.user.ManageUserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageSongFragment :
    BaseFragment<FragmentManageSongBinding>(FragmentManageSongBinding::inflate),
    SongAdapter.OnNewSongClicked {

    private val viewModel by viewModels<ManageSongViewModel>()

    companion object {
        const val TAG = "ManageSongFragment"
    }

    private val songAdapter = SongAdapter(this)

    private var songList: ArrayList<Song> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getNewSongs()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observe()


    }

    private fun observe() {
        viewModel.newSongList.observe(viewLifecycleOwner) {
            binding.rcySong.visibility = View.VISIBLE
            if (it != null) {
                songList = it as ArrayList<Song>
                songAdapter.submitList(it)
            }
            binding.shimmer.stopShimmer()
            binding.shimmer.visibility = View.INVISIBLE


        }


    }


    private fun handleUI() {
        binding.rcySong.adapter = songAdapter


        binding.edtSearch.setOnClickListener {
            val action =
                ManageSongFragmentDirections.actionManageSongFragmentToSearchManageSongFragment()
            findNavController().navigate(action)
        }

        binding.btnAdd.setOnClickListener {
            val action =
                ManageSongFragmentDirections.actionManageSongFragmentToSearchManageSongFragment()
            findNavController().navigate(action)
        }


    }


    override fun onSongClicked(song: Song) {
        TODO("Not yet implemented")
    }

    override fun openMenu(song: Song) {
        TODO("Not yet implemented")
    }


}