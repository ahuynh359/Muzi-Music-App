package com.ahuynh.muzimusicapp.ui.component.library

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.databinding.FragmentLibraryBinding
import com.ahuynh.muzimusicapp.model.Response
import com.ahuynh.muzimusicapp.model.Song
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LibraryFragment : BaseFragment<FragmentLibraryBinding>(FragmentLibraryBinding::inflate),
    OnItemClicked {

    private val viewModel by viewModels<LibraryViewModel>()
    private var adapter = LibraryAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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

    override fun onItemClicked(song: Song) {
    }


}
