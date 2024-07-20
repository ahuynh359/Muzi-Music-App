package com.ahuynh.muzimusicapp.ui.component.admin.type

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.TypeAdapter
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.databinding.FragmentManageTypeBinding
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortBottomSheetFragment
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageTypeFragment :
    BaseFragment<FragmentManageTypeBinding>(FragmentManageTypeBinding::inflate),
    TypeAdapter.OnTypeClicked, SortBottomSheetFragment.SortOptionListener {

    private val viewModel by viewModels<ManageTypeViewModel>({ requireActivity() })

    companion object {
        const val TAG = "ManageTypeFragment"
    }

    private val typeAdapter = TypeAdapter(this)

    private var typeList: ArrayList<Type> = arrayListOf()


    override fun onResume() {
        super.onResume()
        viewModel.getAllTypes()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observe()


    }

    private fun observe() {
        viewModel.typeList.observe(viewLifecycleOwner) {
            binding.rcyType.visibility = View.VISIBLE
            if (it != null) {
                typeList = it as ArrayList<Type>
                typeAdapter.submitList(it)
            }
            binding.shimmer.stopShimmer()
            binding.shimmer.visibility = View.INVISIBLE


        }
        viewModel.sortType.observe(viewLifecycleOwner){
            binding.btnSort.text = it.name
            viewModel.getAllTypes()
        }



    }


    private fun handleUI() {
        binding.rcyType.adapter = typeAdapter


        binding.edtSearch.setOnClickListener {
            val action =
                ManageTypeFragmentDirections.actionManageTypeFragmentToSearchManageTypeFragment()
            findNavController().navigate(action)
        }

        binding.btnAdd.setOnClickListener {
            val action = ManageTypeFragmentDirections.actionManageTypeFragmentToAddTypeFragment()
            findNavController().navigate(action)
        }
        binding.btnSort.setOnClickListener {
            val sortBottomSheet = SortBottomSheetFragment()
            sortBottomSheet.listener = this
            sortBottomSheet.show(parentFragmentManager, null)
        }


    }

    override fun onTypeClicked(type: Type) {
        val action =
            ManageTypeFragmentDirections.actionManageTypeFragmentToManageTypeDetailFragment(type)
        findNavController().navigate(action)
    }

    override fun onMoreClicked(type: Type) {
        val action =
            ManageTypeFragmentDirections.actionManageTypeFragmentToManageTypeMenu(type)
        findNavController().navigate(action)
    }

    override fun onSortOptionSelected(name: SortName) {
        when (name) {
            SortName.NEW -> {
                viewModel.setSortType(SortName.NEW)
            }

            SortName.OLD -> {
                viewModel.setSortType(SortName.OLD)
            }

            SortName.A_Z -> {
                viewModel.setSortType(SortName.A_Z)

            }

            SortName.Z_A -> {
                viewModel.setSortType(SortName.Z_A)
            }

            else -> { viewModel.setSortType(SortName.NEW)}


        }
    }


}
