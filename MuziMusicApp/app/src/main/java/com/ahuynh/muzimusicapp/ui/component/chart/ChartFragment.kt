package com.ahuynh.muzimusicapp.ui.component.chart

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.adapter.ChartAdapter
import com.ahuynh.muzimusicapp.adapter.OnSongChartClicked
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentChartBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.ui.component.song.SongViewModel
import com.ahuynh.muzimusicapp.utils.Utils
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartFragment : BaseFragment<FragmentChartBinding>(FragmentChartBinding::inflate),
    OnSongChartClicked {
    private val viewModel by viewModels<SongViewModel>({ requireActivity() })

    companion object {
        const val TAG = "ChartFragment"
    }

    private var listSong: ArrayList<Song> = arrayListOf()
    private val chartAdapter = ChartAdapter(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
    }

    private fun handleUI() {
        binding.rcySong.adapter = chartAdapter
        viewModel.getAllSongByListen()
        viewModel.songList.observe(viewLifecycleOwner) {
            chartAdapter.setData(it)
            binding.rcySong.visibility = View.VISIBLE
            listSong = it as ArrayList<Song>
            hideShimmer()

        }
        setUpChart()
    }

    override fun onSongClicked(song: Song) {
        viewModel.updateSongListen(song)
        viewModel.getAllSongByListen()
        startActivity(Intent(requireContext(), PlayerActivity::class.java))
        Utils.sendMusic(
            requireContext(),
            MusicService.ACTION_PLAY,
            song, listSong
        )

    }

    private fun hideShimmer() {
        binding.shimmerSong.stopShimmer()
        binding.shimmerSong.visibility = View.GONE
    }

    private fun setUpChart() {



        // Create a list of data points for the first dataset
        val entries1 = mutableListOf<Entry>()
        entries1.add(Entry(1f, 20f))
        entries1.add(Entry(2f, 40f))
        entries1.add(Entry(3f, 30f))
        entries1.add(Entry(4f, 50f))
        entries1.add(Entry(5f, 60f))

        // Create a LineDataSet for the first dataset
        val dataSet1 = LineDataSet(entries1, "DataSet 1")
        dataSet1.color = Color.WHITE

        // Create a list of data points for the second dataset
        val entries2 = mutableListOf<Entry>()
        entries2.add(Entry(1f, 30f))
        entries2.add(Entry(2f, 50f))
        entries2.add(Entry(3f, 40f))
        entries2.add(Entry(4f, 60f))
        entries2.add(Entry(5f, 70f))

        // Create a LineDataSet for the second dataset
        val dataSet2 = LineDataSet(entries2, "DataSet 2")
        dataSet2.color = Color.GREEN // Set color for the second dataset

        // Create a list of data points for the third dataset
        val entries3 = mutableListOf<Entry>()
        entries3.add(Entry(1f, 10f))
        entries3.add(Entry(2f, 30f))
        entries3.add(Entry(3f, 20f))
        entries3.add(Entry(4f, 40f))
        entries3.add(Entry(5f, 50f))

        // Create a LineDataSet for the third dataset
        val dataSet3 = LineDataSet(entries3, "DataSet 3")
        dataSet3.color = Color.BLUE // Set color for the third dataset

        // Create a LineData object with all LineDataSet objects
        val lineData = LineData(dataSet1, dataSet2, dataSet3)

        // Set the LineData to the LineChart
        binding.chart.data = lineData

        // Disable description, X axis, right Y axis, and enable legend
        binding.chart.description.isEnabled = false
        binding.chart.xAxis.isEnabled = false
        binding.chart.axisRight.isEnabled = false
        binding.chart.legend.isEnabled = true
        binding.chart.legend.textColor = Color.WHITE

        // Animate the chart
        binding.chart.animateY(1000)

        // Set axis and grid line colors to white
        binding.chart.axisLeft.textColor = Color.WHITE
        binding.chart.axisLeft.gridColor = Color.WHITE

        // Invalidate and refresh the chart
        binding.chart.invalidate()
    }


}