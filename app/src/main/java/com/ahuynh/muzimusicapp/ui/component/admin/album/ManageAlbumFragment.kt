package com.ahuynh.muzimusicapp.ui.component.admin.album

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.AlbumAdapter
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.databinding.FragmentManageAlbumBinding
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortBottomSheetFragment
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageAlbumFragment :
    BaseFragment<FragmentManageAlbumBinding>(FragmentManageAlbumBinding::inflate),
    AlbumAdapter.OnAlbumClicked, SortBottomSheetFragment.SortOptionListener {

    private val viewModel by viewModels<ManageAlbumViewModel>({ requireActivity() })

    companion object {
        const val TAG = "ManageAlbumFragment"
    }

    private val albumAdapter = AlbumAdapter(this)

    private var albumList: ArrayList<Album> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getNewAlbums()
        Log.d("ABC","On Create")
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observe()


    }

    private fun observe() {
        viewModel.albumList.observe(viewLifecycleOwner) {
            binding.rcyAlbum.visibility = View.VISIBLE
            if (it != null) {
                albumList = it as ArrayList<Album>
                albumAdapter.submitList(it)
            }
            binding.shimmer.stopShimmer()
            binding.shimmer.visibility = View.INVISIBLE


        }
        viewModel.sortOrder.observe(viewLifecycleOwner){
            binding.btnSort.text = it
        }




    }


    private fun handleUI() {
        binding.rcyAlbum.adapter = albumAdapter


        binding.edtSearch.setOnClickListener {
            val action =
                ManageAlbumFragmentDirections.actionManageAlbumFragmentToSearchManageAlbumFragment()
            findNavController().navigate(action)
        }

        binding.btnAdd.setOnClickListener {
            val action = ManageAlbumFragmentDirections.actionManageAlbumFragmentToAddAlbumFragment()
            findNavController().navigate(action)
        }
        binding.btnSort.setOnClickListener {
            val sortBottomSheet = SortBottomSheetFragment()
            sortBottomSheet.listener = this
            sortBottomSheet.show(parentFragmentManager, null)
        }


    }

    override fun onAlbumClicked(album: Album) {
        val action =
            ManageAlbumFragmentDirections.actionManageAlbumFragmentToManageAlbumDetailFragment(album)
        findNavController().navigate(action)
    }

    override fun onMoreItemAlbumClicked(album: Album) {
        val action =
            ManageAlbumFragmentDirections.actionManageAlbumFragmentToManageAlbumMenu(album)
        findNavController().navigate(action)
    }



    override fun onSortOptionSelected(name: SortName) {
        when (name) {
            SortName.NEW -> {
                //binding.btnSort.text = getString(R.string.new_a)
                albumList.sortByDescending { it.createdAt }
                albumAdapter.submitList(albumList.toList())
                viewModel.sortOrder.postValue(SortName.NEW.name)
            }

            SortName.OLD -> {
                //binding.btnSort.text = getString(R.string.old)
                albumList.sortBy { it.createdAt }
                albumAdapter.submitList(albumList.toList())
                viewModel.sortOrder.postValue(SortName.OLD.name)
            }

            SortName.A_Z -> {
                //binding.btnSort.text = getString(R.string.a_z)
                albumList.sortBy { it.name }
                albumAdapter.submitList(albumList.toList())
                viewModel.sortOrder.postValue(SortName.A_Z.name)
            }

            SortName.Z_A -> {
                //binding.btnSort.text = getString(R.string.z_a)
                albumList.sortByDescending { it.name }
                albumAdapter.submitList(albumList.toList())
                viewModel.sortOrder.postValue(SortName.Z_A.name)
            } else ->{

            }
        }
    }


}
