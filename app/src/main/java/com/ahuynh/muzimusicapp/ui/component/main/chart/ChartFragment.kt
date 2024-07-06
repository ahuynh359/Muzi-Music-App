package com.ahuynh.muzimusicapp.ui.component.main.chart

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.adapter.ChartAdapter
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentChartBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.main.song.menu.SongMenu
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartFragment : BaseFragment<FragmentChartBinding>(FragmentChartBinding::inflate),

    ChartAdapter.OnChartClicked{

    private val viewModel by viewModels<ChartViewModel>()

    companion object {
        const val TAG = "ChartFragment"
    }

    private val chartSongAdapter = ChartAdapter(this)
    private var chartList: ArrayList<Song> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observe()


    }

    private fun observe() {

        handleChartList()



    }



    private fun handleChartList() {
        binding.rcySong.adapter = chartSongAdapter
        viewModel.chartList.observe(viewLifecycleOwner) {
            binding.rcySong.visibility = View.VISIBLE
            if (it != null) {
                chartList = it as ArrayList<Song>
                chartSongAdapter.submitList(it)
            }
            binding.shimmerSong.stopShimmer()
            binding.shimmerSong.visibility = View.INVISIBLE


        }
    }



    private fun handleUI() {


    }

    override fun onSongClicked(song: Song) {
        startActivity(Intent(context, PlayerActivity::class.java))
        Utils.sendMusic(
            requireContext(),
            MusicService.ACTION_PLAY,
            song,
            chartList
        )
    }

    override fun openMenu(song: Song) {
        SongMenu().apply {
            arguments = Bundle().apply {
                putParcelable(Constants.SONG, song)
            }
        }.show(requireActivity().supportFragmentManager, null)
    }





}
