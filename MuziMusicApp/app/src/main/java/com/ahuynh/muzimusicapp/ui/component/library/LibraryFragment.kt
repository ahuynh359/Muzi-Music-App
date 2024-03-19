package com.ahuynh.muzimusicapp.ui.component.library

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.databinding.FragmentLibraryBinding
import com.ahuynh.muzimusicapp.model.Song
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.utils.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LibraryFragment : BaseFragment<FragmentLibraryBinding>(FragmentLibraryBinding::inflate),
    OnItemClicked {

    private val viewModel by viewModels<LibraryViewModel>()
    private var adapter = LibraryAdapter(this)
    private lateinit var snackbar: Snackbar


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        snackbar = Snackbar.make(
            binding.rootView,
            "No Internet Connection",
            Snackbar.LENGTH_LONG
        ).setAction("Wifi") {
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }


        networkConnectivityObserver.observe(viewLifecycleOwner) {
            when (it) {
                Status.Available -> {
                    binding.tvLibrary.text = "OK"
                    if (snackbar.isShown) {
                        snackbar.dismiss()
                    }
                }

                else -> {
                    binding.tvLibrary.text = "Not OK"
                    snackbar.show()
                }
            }
        viewModel.getAllSongs().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Loading -> {

                    Log.d("LibraryFragment","Loading")
                }
                is Response.Success -> {
                    adapter.submitList(response.data)
                    binding.shimmerRecyclerview.stopShimmer()
                    binding.rcyItem.visibility = View.VISIBLE
                    binding.shimmerRecyclerview.visibility = View.GONE
                    Log.d("LibraryFragment","Success")
                    Log.d("LibraryFragment",response.data.toString())
                }

                is Response.Failure -> {
                    print(response.errorMessage)
                    Log.d("LibraryFragment",response.errorMessage)
                }
            }
        }

        binding.rcyItem.adapter = adapter


    }


    }

    override fun onItemClicked(song: Song) {
        TODO("Not yet implemented")
    }
}
