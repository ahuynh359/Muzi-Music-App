package com.ahuynh.muzimusicapp.ui.component.admin.singer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.SingerAdapter
import com.ahuynh.muzimusicapp.adapter.SingerViewType
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.databinding.FragmentManageSingerBinding
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortBottomSheetFragment
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageSingerFragment :
    BaseFragment<FragmentManageSingerBinding>(FragmentManageSingerBinding::inflate),
    SingerAdapter.OnSingerClicked, SortBottomSheetFragment.SortOptionListener {

   private val viewModel by viewModels<ManageSingerViewModel>({requireActivity()})

    companion object {
        const val TAG = "ManageSingerFragment"
    }

    private val singerAdapter = SingerAdapter(this,SingerViewType.LIST)

    private var singerList: ArrayList<Singer> = arrayListOf()


    override fun onResume() {
        super.onResume()
        viewModel.getAllSingers()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observe()


    }

    private fun observe() {
        viewModel.singerList.observe(viewLifecycleOwner) {
            binding.rcySinger.visibility = View.VISIBLE
            if (it != null) {
                singerList = it as ArrayList<Singer>
                singerAdapter.submitList(it)
            }
            binding.shimmer.stopShimmer()
            binding.shimmer.visibility = View.INVISIBLE


        }
        viewModel.sortSinger.observe(viewLifecycleOwner) {
            binding.btnSort.text = it.name
            viewModel.getAllSingers()
        }


    }


    private fun handleUI() {
        binding.rcySinger.adapter = singerAdapter


        binding.edtSearch.setOnClickListener {
            val action =
                ManageSingerFragmentDirections.actionManageSingerFragmentToSearchManageSingerFragment()
            findNavController().navigate(action)
        }

        binding.btnAdd.setOnClickListener {
            val action =
                ManageSingerFragmentDirections.actionManageSingerFragmentToAddSingerFragment()
            findNavController().navigate(action)
        }
        binding.btnSort.setOnClickListener {
            val sortBottomSheet = SortBottomSheetFragment()
            sortBottomSheet.listener = this
            sortBottomSheet.show(parentFragmentManager, null)
        }


    }

    override fun onSingerClicked(singer: Singer) {
        val action =
            ManageSingerFragmentDirections.actionManageSingerFragmentToManageSingerDetailFragment(
                singer
            )
        findNavController().navigate(action)
    }



    override fun onSortOptionSelected(name: SortName) {
        when (name) {
            SortName.NEW -> {
                viewModel.setSortSinger(SortName.NEW)
            }

            SortName.OLD -> {
                viewModel.setSortSinger(SortName.OLD)
            }

            SortName.A_Z -> {
                viewModel.setSortSinger(SortName.A_Z)

            }

            SortName.Z_A -> {
                viewModel.setSortSinger(SortName.Z_A)
            }

            else -> {
                viewModel.setSortSinger(SortName.NEW)
            }


        }
    }


}
