package com.ahuynh.muzimusicapp.ui.component.library

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.databinding.FragmentLibraryBinding
import com.ahuynh.muzimusicapp.model.Song
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Constants.ACTION_PLAY
import com.ahuynh.muzimusicapp.utils.Helper
import com.ahuynh.muzimusicapp.utils.Response
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LibraryFragment : BaseFragment<FragmentLibraryBinding>(FragmentLibraryBinding::inflate),
    OnItemClicked {

    private val viewModel by viewModels<LibraryViewModel>()
    private var adapter = LibraryAdapter(this)
    private lateinit var snackbar: Snackbar
    private var listSong: ArrayList<Song> = arrayListOf()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        snackbar = Snackbar.make(
            binding.swipeRefresh,
            "No Internet Connection",
            Snackbar.LENGTH_INDEFINITE
        ).setAction("Wifi") {
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }
        observers()
        binding.rcyItem.adapter = adapter
        fetchData()

        binding.swipeRefresh.setOnRefreshListener {
            fetchData()
        }


    }

    private fun fetchData() {
        viewModel.getSong()
    }


    private fun observers() {
        //Observe network change
        networkConnectivityObserver.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    if (snackbar.isShown) {
                        snackbar.dismiss()
                    }
                }

                else -> {
                    snackbar.show()
                }
            }
        }

        //Observe songs
        viewModel.song.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Loading -> {
                    Log.d("LibraryFragment", "Loading")
                }

                is Response.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    adapter.submitList(response.data)
                    listSong = response.data as ArrayList<Song>
                    binding.shimmerRecyclerview.stopShimmer()
                    binding.rcyItem.visibility = View.VISIBLE
                    binding.shimmerRecyclerview.visibility = View.GONE
                    Log.d("LibraryFragment", listSong.toString())
                }

                is Response.Failure -> {
                    Log.d("LibraryFragment", response.errorMessage)
                }
            }
        }




    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClicked(song: Song) {
        Toast.makeText(requireContext(),song.name,Toast.LENGTH_SHORT).show()
        startActivity(Intent(requireContext(), PlayerActivity::class.java))

        Helper.sendMusic(
            requireContext(),
            ACTION_PLAY,
            song,
            songList = listSong
        )



    }
}