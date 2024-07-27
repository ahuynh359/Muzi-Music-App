package com.ahuynh.muzimusicapp.ui.component.admin.song

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortBottomSheetFragment
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.song.add_song.UploadSongActivity
import com.ahuynh.muzimusicapp.ui.component.admin.user.ManageUserFragmentDirections
import com.ahuynh.muzimusicapp.ui.component.admin.user.ManageUserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageSongFragment :
    BaseFragment<FragmentManageSongBinding>(FragmentManageSongBinding::inflate),
    SongAdapter.OnSongClicked, SortBottomSheetFragment.SortOptionListener {

    private val viewModel by viewModels<ManageSongViewModel>()

    companion object {
        const val TAG = "ManageSongFragment"
    }

    private val songAdapter = SongAdapter(this)

    private var songList: ArrayList<Song> = arrayListOf()


    override fun onResume() {
        super.onResume()
        viewModel.getAllSongs()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observe()


    }

    private fun observe() {
        viewModel.songList.observe(viewLifecycleOwner) {
            binding.rcySong.visibility = View.VISIBLE
            if (it != null) {
                songList = it as ArrayList<Song>
                songAdapter.submitList(it)
            }
            binding.shimmer.stopShimmer()
            binding.shimmer.visibility = View.INVISIBLE


        }

        viewModel.deleteSongStatus.observe(viewLifecycleOwner) {
            it?.let {
                if(it){
                    viewModel.getAllSongs()
                }
                viewModel.mess?.let { mess ->
                    Toast.makeText(requireContext(), mess, Toast.LENGTH_LONG).show()
                }

            }
            viewModel.deleteSongStatus.postValue(null)

        }

        viewModel.sortSong.observe(viewLifecycleOwner) {
            binding.btnSort.text = it.name
            viewModel.getAllSongs()
        }
        viewModel.deleteSongStatus.observe(viewLifecycleOwner) {
            if (it == true) {
                viewModel.getAllSongs()
            }

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
            startActivity(Intent(activity, UploadSongActivity::class.java))
        }

        binding.btnSort.setOnClickListener {
            val sortBottomSheet = SortBottomSheetFragment()
            sortBottomSheet.listener = this
            sortBottomSheet.show(parentFragmentManager, null)
        }


    }


    override fun onSongClicked(song: Song) {
        val action =
            ManageSongFragmentDirections.actionManageSongFragmentToManageSongDetailFragment(song)
        findNavController().navigate(action)
    }

    override fun openMenu(song: Song) {
        val action = ManageSongFragmentDirections.actionManageSongFragmentToManageSongMenu(song)
        findNavController().navigate(action)
    }

    override fun onSortOptionSelected(name: SortName) {
        when (name) {
            SortName.NEW -> {
                viewModel.setSortSong(SortName.NEW)
            }

            SortName.OLD -> {
                viewModel.setSortSong(SortName.OLD)
            }

            SortName.A_Z -> {
                viewModel.setSortSong(SortName.A_Z)

            }

            SortName.Z_A -> {
                viewModel.setSortSong(SortName.Z_A)
            }

            else -> {
                viewModel.setSortSong(SortName.NEW)
            }


        }
    }


}