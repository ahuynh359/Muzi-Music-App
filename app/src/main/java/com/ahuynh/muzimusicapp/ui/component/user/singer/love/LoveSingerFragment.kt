package com.ahuynh.muzimusicapp.ui.component.user.singer.love

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.SingerAdapter
import com.ahuynh.muzimusicapp.adapter.SingerAdapter.SingerViewType
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.databinding.FragmentLoveSingerBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.user.singer.SingerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoveSingerFragment :
    BaseFragment<FragmentLoveSingerBinding>(FragmentLoveSingerBinding::inflate),
    SingerAdapter.OnSingerClicked {

    companion object {
        const val TAG = "SingerFragment"
    }

    private val singerAdapter = SingerAdapter(this, SingerViewType.LIST)
    private val viewModel by viewModels<SingerViewModel>()
    private lateinit var singerList: ArrayList<Singer>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
        observe()

    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        viewModel.getAllSinger()
    }

    private fun observe() {

        viewModel.singerList.observe(viewLifecycleOwner) {
            singerAdapter.submitList(it)
            singerList = it as ArrayList<Singer>
            binding.rcySinger.visibility = View.VISIBLE
            if (it.isEmpty()) {
                binding.tvNoSinger.visibility = View.VISIBLE
            } else {
                binding.tvNoSinger.visibility = View.GONE
            }
            binding.shimmer.stopShimmer()
            binding.shimmer.visibility = View.GONE
            binding.swipeRefresh.isRefreshing = false
        }


    }


    private fun handleUI() {
        binding.swipeRefresh.setOnRefreshListener {
            getData()

        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.rcySinger.adapter = singerAdapter

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.tvNoSinger.visibility =
                        if (singerList.isEmpty()) View.VISIBLE else View.GONE
                    singerAdapter.submitList(singerList)
                } else {
                    filterSingers(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterSingers(query: String) {
        val filteredList = singerList.filter { singer ->
            singer.name.contains(query, ignoreCase = true)
        }
        binding.tvNoSinger.visibility =
            if (filteredList.isEmpty() || singerList.isEmpty()) View.VISIBLE else View.GONE
        singerAdapter.submitList(filteredList)
    }


    override fun onSingerClicked(singer: Singer) {
        val action =
            LoveSingerFragmentDirections.actionLoveSingerFragmentToSingerDetailFragment(singer)
        findNavController().navigate(action)
    }


}