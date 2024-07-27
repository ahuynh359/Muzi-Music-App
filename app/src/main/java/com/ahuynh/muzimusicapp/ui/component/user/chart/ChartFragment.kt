package com.ahuynh.muzimusicapp.ui.component.user.chart

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.ChartAdapter
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentChartBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.user.song.menu.SongMenu
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils
import com.github.mikephil.charting.data.Entry
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartFragment : BaseFragment<FragmentChartBinding>(FragmentChartBinding::inflate),

    ChartAdapter.OnChartClicked{

    private val viewModel by viewModels<ChartViewModel>()
    private val values : ArrayList<ArrayList<Entry>> = ArrayList()
    private val colorsTopSong = listOf(
        Color.rgb(47,148,240),
        Color.rgb(56,202,147),
        Color.rgb(227,121,68)
    )

    companion object {
        const val TAG = "ChartFragment"
    }

    private val chartSongAdapter = ChartAdapter(this)
    private var chartList: ArrayList<Song> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setUpChart()
        handleUI()
        observe()


    }


//    private fun setUpChart() {
//        val list = chartList.subList(0, min(3,chartList.size))
//
//
//        binding.lineChart.visibility = View.VISIBLE
//        // Create a list of data points for the first dataset
//        val entries1 = mutableListOf<Entry>()
//        var index = 0
//        for (i in list[0].listens) {
//            entries1.add(Entry(index.toFloat(), i.value.toFloat()))
//            index++
//        }
//
//
//        // Create a LineDataSet for the first dataset
//        val dataSet1 = LineDataSet(entries1, list[0].name)
//        dataSet1.color = Color.RED
//
//        // Create a list of data points for the second dataset
//        val entries2 = mutableListOf<Entry>()
//        index = 0
//        for (i in list[1].listens) {
//            entries2.add(Entry(index.toFloat(), i.value.toFloat()))
//            index++
//        }
//
//        // Create a LineDataSet for the second dataset
//        val dataSet2 = LineDataSet(entries2, list[1].name)
//        dataSet2.color = Color.GREEN // Set color for the second dataset
//
//        // Create a list of data points for the third dataset
//        val entries3 = mutableListOf<Entry>()
//        index = 0
//        for (i in list[2].listens) {
//            entries3.add(Entry(index.toFloat(), i.value.toFloat()))
//            index++
//        }
//
//        // Create a LineDataSet for the third dataset
//        val dataSet3 = LineDataSet(entries3, list[2].name)
//        dataSet3.color = Color.YELLOW // Set color for the third dataset
//
//        // Create a LineData object with all LineDataSet objects
//        val lineData = LineData(dataSet1, dataSet2, dataSet3)
//
//        // Set the LineData to the LineChart
//        binding.lineChart.data = lineData
//
//        // Disable description, X axis, right Y axis, and enable legend
//        binding.lineChart.description.isEnabled = false
//        binding.lineChart.axisRight.isEnabled = false
//        binding.lineChart.legend.isEnabled = true
//        binding.lineChart.legend.textColor = Color.WHITE
//
//        binding.lineChart.xAxis.textColor = Color.WHITE
//        binding.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
//        val days = listOf("24-04", "25-04", "26-04", "27-04","28-04","29-04")
//        binding.barChart.xAxis.labelRotationAngle = 45f
//        binding.lineChart.xAxis.valueFormatter = object : ValueFormatter() {
//            override fun getFormattedValue(value: Float): String {
//                if (value == 0f) {
//                    return days[0]
//                } else if (value == 1f) {
//                    return days[1]
//                } else if (value == 2f) {
//                    return days[2]
//                } else if (value == 3f) {
//                    return days[3]
//                }
//                else if (value == 4f) {
//                    return days[4]
//                }
//                return "...";
//            }
//        }
//        // Animate the chart
//        binding.lineChart.animateY(1000)
//
//        // Set axis and grid line colors to white
//        binding.lineChart.axisLeft.textColor = Color.WHITE
//        binding.lineChart.axisLeft.gridColor = Color.WHITE
//
//        // Invalidate and refresh the chart
//        binding.lineChart.invalidate()
//    }

    private fun observe() {

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
        val fragment = SongMenu()
        fragment.arguments = Bundle().apply {
            putParcelable(Constants.SONG,song)
        }
        fragment.show(requireActivity().supportFragmentManager,null)
    }





}
