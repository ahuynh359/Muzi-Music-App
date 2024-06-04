package com.ahuynh.muzimusicapp.ui.component.search

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.NewSongAdapter
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.data.model.SongOld
import com.ahuynh.muzimusicapp.data_api.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentSearchBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.ui.component.song.SongFragmentDirections
import com.ahuynh.muzimusicapp.ui.component.song.SongViewModel
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils
import com.ahuynh.muzimusicapp.utils.helper.ToastHelper.makeErrorToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    NewSongAdapter.OnNewSongClicked {
    private val viewModel by viewModels<SongViewModel>({ requireActivity() })
    private lateinit var adapter: NewSongAdapter

    companion object {
        const val TAG = "SearchFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NewSongAdapter(this)
        binding.rcySong.adapter = adapter
        observe()
        handleUI()
    }

    private fun handleUI() {
        binding.searchView.clearFocus()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Xử lý tìm kiếm khi người dùng nhấn Enter
                if (!query.isNullOrEmpty()) {
                    performSearch(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    performSearch(newText)
                }
                return true
            }
        })
    }

    private fun observe() {

    }

    private fun performSearch(query: String) {
//        val songOldList = mutableListOf<SongOld>()
//        for (s in Constants.SONG_Old_LIST_DATA) {
//            if ((s.name?.lowercase()?.contains(query.lowercase()) == true) || (s.singer?.lowercase()?.contains(query.lowercase()) == true) ) {
//                songOldList.add(s)
//            }
//        }
//        if (songOldList.isEmpty()) {
//            makeErrorToast(requireContext(), "Don't have this song")
//            adapter.submitList(arrayListOf())
//        } else {
//            adapter.submitList(songOldList)
//        }
    }


    fun EditText.showKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    fun EditText.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.windowToken, 0)
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onSongClicked(songOld: SongOld) {
////        viewModel.updateSongListen(songOld)
////        viewModel.updateSongWithCurrentDate(songOld, Utils.getCurrentDateAsString())
//
//        startActivity(Intent(requireContext(), PlayerActivity::class.java))
//        Utils.sendMusic(
//            requireContext(),
//            MusicService.ACTION_PLAY,
//            songOld, arrayListOf()
//        )
//
//
//    }
//
//    override fun openMenu(songOld: SongOld) {
//        val action = SongFragmentDirections.actionSongFragmentToSongMenuBottom(songOld)
//        findNavController().navigate(action)
//
//    }

    override fun onSongClicked(song: Song) {
        TODO("Not yet implemented")
    }

    override fun openMenu(song: Song) {
        TODO("Not yet implemented")
    }
}