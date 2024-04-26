package com.ahuynh.muzimusicapp.ui.component.chart

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.ChartAdapter
import com.ahuynh.muzimusicapp.adapter.OnSongChartClicked
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentChartBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.ui.component.song.SongViewModel
import com.ahuynh.muzimusicapp.utils.Utils
import com.ahuynh.muzimusicapp.utils.Utils.getCurrentDateAsString
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

@AndroidEntryPoint
class ChartFragment : BaseFragment<FragmentChartBinding>(FragmentChartBinding::inflate),
    OnSongChartClicked {
    private val viewModel by viewModels<SongViewModel>({ requireActivity() })

    companion object {
        const val TAG = "ChartFragment"
    }

    private val REQUEST_CODE = 1

    private var listSong: ArrayList<Song> = arrayListOf()
    private val chartAdapter = ChartAdapter(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()

    }

    private fun handleUI() {
        binding.rcySong.adapter = chartAdapter
        viewModel.getAllSongByListen()
        viewModel.listenSongList.observe(viewLifecycleOwner) {
            chartAdapter.setData(it)
            binding.rcySong.visibility = View.VISIBLE
            listSong = it as ArrayList<Song>
            hideShimmer()

            if (viewModel.sortIndex.value == 0) {
                disableChart()
                setUpChartByDay()
            } else if (viewModel.sortIndex.value == 1) {
                disableChart()
                setUpChartByMonth()
            } else if (viewModel.sortIndex.value == 2) {
                disableChart()
                setUpChartByYear()
            }

        }
        binding.btnExport.setOnClickListener {
            // Check and request permission to write to external storage if needed

            if (viewModel.sortIndex.value == 0) {

                exportToCSVByDay()

            } else if (viewModel.sortIndex.value == 1) {
                exportCsvByMonth()

            } else if (viewModel.sortIndex.value == 2) {

            }
        }


    }


    private fun createMuziMusicDirectory() {
        val folderName = "MuziMusic"

        // Get the DCIM directory
        val dcimDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)

        // Create the MuziMusic directory inside DCIM
        val muziMusicDirectory = File(dcimDirectory, folderName)

        // Check if the directory exists
        if (!muziMusicDirectory.exists()) {
            // Create the directory if it doesn't exist
            if (muziMusicDirectory.mkdirs()) {
                Log.d(TAG, "MuziMusic directory created successfully")
            } else {
                Log.e(TAG, "Failed to create MuziMusic directory")
            }
        } else {
            Log.d(TAG, "MuziMusic directory already exists")
        }
    }

    private fun exportToCSVByDay() {
        val data = mutableListOf<List<String>>()

        listSong.forEach { song ->
            data.add(listOf(song.name, song.listen,"\n") as List<String>)

        }

        val folderName = "MuziMusic"
        val fileName = "data_by_day.csv"

        val dcimDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        val muziMusicDirectory = File(dcimDirectory, folderName)

        if (!muziMusicDirectory.exists()) {
            if (!muziMusicDirectory.mkdirs()) {
                Log.e(TAG, "Failed to create MuziMusic directory")
                return
            }
        }

        val csvFile = File(muziMusicDirectory, fileName)

        try {
            val fileOutputStream = FileOutputStream(csvFile)

            val outputStreamWriter = OutputStreamWriter(fileOutputStream)

            outputStreamWriter.write(data.toString())
            outputStreamWriter.close()
            fileOutputStream.close()

            Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e(TAG, "Error saving CSV file: ${e.message}")
        }
    }

    private fun exportCsvByMonth() {
        val data = mutableListOf<List<String>>()

        listSong.forEach { song ->
            for (i in song.listens)
                data.add(
                    listOf(
                        song.name,
                        song.listen,
                        i.key,
                        i.value,
                        "\n"
                    ) as List<String>
                )

        }

        val folderName = "MuziMusic"
        val fileName = "data_by_month.csv"

        val dcimDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        val muziMusicDirectory = File(dcimDirectory, folderName)

        if (!muziMusicDirectory.exists()) {
            if (!muziMusicDirectory.mkdirs()) {
                Log.e(TAG, "Failed to create MuziMusic directory")
                return
            }
        }

        val csvFile = File(muziMusicDirectory, fileName)

        try {
            val fileOutputStream = FileOutputStream(csvFile)

            val outputStreamWriter = OutputStreamWriter(fileOutputStream)

            outputStreamWriter.write(data.toString())
            outputStreamWriter.close()
            fileOutputStream.close()

            Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e(TAG, "Error saving CSV file: ${e.message}")
        }
    }

    override fun onResume() {
        super.onResume()

        val items = listOf("Day", "Month", "Year")
        val adapter = ArrayAdapter<String>(requireContext(), R.layout.item, items)
        binding.select.setAdapter(adapter)

        binding.select.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                viewModel.sortIndex.value = i
                val itemSelect = adapterView.getItemAtPosition(i)
                Toast.makeText(requireContext(), itemSelect.toString(), Toast.LENGTH_SHORT).show()
                viewModel.listenSongList.observe(viewLifecycleOwner) {
                    if (viewModel.sortIndex.value == 0) {
                        disableChart()
                        setUpChartByDay()
                    } else if (viewModel.sortIndex.value == 1) {
                        disableChart()
                        setUpChartByMonth()
                    } else if (viewModel.sortIndex.value == 2) {
                        disableChart()
                        setUpChartByYear()
                    }

                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSongClicked(song: Song) {

        viewModel.updateSongListen(song)
        viewModel.updateSongWithCurrentDate(song, getCurrentDateAsString())
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

    private fun setUpChartByDay() {
        binding.barChart.visibility = View.VISIBLE
        val list = listSong.subList(0, 3)

        val barEntries = ArrayList<BarEntry>()
        val colors = mutableListOf<Int>(
            Color.RED,
            Color.GREEN,
            Color.YELLOW,
            Color.RED,
        )
        for ((index, item) in list.withIndex()) {
            val barEntry = BarEntry(index.toFloat(), (item.listen?.toInt() ?: 0).toFloat())
            barEntries.add(barEntry)
        }


        val dataSet = BarDataSet(barEntries, "Listens")
        dataSet.colors = colors.subList(0, barEntries.size % colors.size) // Màu của cột
        dataSet.valueTextColor = Color.WHITE
        val data = BarData(dataSet)
        binding.barChart.data = data
        binding.barChart.setFitBars(true)
        binding.barChart.description.isEnabled = false
        val xAxisLabels = list.map { it.name }
        binding.barChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)
        binding.barChart.xAxis.labelRotationAngle = 45f // Xoay các nhãn 45 độ


        binding.barChart.xAxis.textColor = Color.WHITE
        binding.barChart.axisLeft.textColor = Color.WHITE
        binding.barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.barChart.invalidate()
    }

    private fun disableChart() {
        binding.lineChart.visibility = View.INVISIBLE
        binding.barChart.visibility = View.INVISIBLE
    }

    private fun setUpChartByYear() {

    }


    private fun setUpChartByMonth() {
        val list = listSong.subList(0, 3)


        binding.lineChart.visibility = View.VISIBLE
        // Create a list of data points for the first dataset
        val entries1 = mutableListOf<Entry>()
        var index = 0
        for (i in list[0].listens) {
            entries1.add(Entry(index.toFloat(), i.value.toFloat()))
            index++
        }


        // Create a LineDataSet for the first dataset
        val dataSet1 = LineDataSet(entries1, list[0].name)
        dataSet1.color = Color.RED

        // Create a list of data points for the second dataset
        val entries2 = mutableListOf<Entry>()
        index = 0
        for (i in list[1].listens) {
            entries2.add(Entry(index.toFloat(), i.value.toFloat()))
            index++
        }

        // Create a LineDataSet for the second dataset
        val dataSet2 = LineDataSet(entries2, list[1].name)
        dataSet2.color = Color.GREEN // Set color for the second dataset

        // Create a list of data points for the third dataset
        val entries3 = mutableListOf<Entry>()
        index = 0
        for (i in list[2].listens) {
            entries3.add(Entry(index.toFloat(), i.value.toFloat()))
            index++
        }

        // Create a LineDataSet for the third dataset
        val dataSet3 = LineDataSet(entries3, list[2].name)
        dataSet3.color = Color.YELLOW // Set color for the third dataset

        // Create a LineData object with all LineDataSet objects
        val lineData = LineData(dataSet1, dataSet2, dataSet3)

        // Set the LineData to the LineChart
        binding.lineChart.data = lineData

        // Disable description, X axis, right Y axis, and enable legend
        binding.lineChart.description.isEnabled = false
        binding.lineChart.axisRight.isEnabled = false
        binding.lineChart.legend.isEnabled = true
        binding.lineChart.legend.textColor = Color.WHITE

        binding.lineChart.xAxis.textColor = Color.WHITE
        binding.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        val days = listOf("24-04", "25-04", "26-04", "27-04")
        binding.barChart.xAxis.labelRotationAngle = 45f
        binding.lineChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                if (value == 0f) {
                    return days[0]
                } else if (value == 1f) {
                    return days[1]
                } else if (value == 2f) {
                    return days[2]
                } else if (value == 3f) {
                    return days[3]
                }
                return "";
            }
        }
        // Animate the chart
        binding.lineChart.animateY(1000)

        // Set axis and grid line colors to white
        binding.lineChart.axisLeft.textColor = Color.WHITE
        binding.lineChart.axisLeft.gridColor = Color.WHITE

        // Invalidate and refresh the chart
        binding.lineChart.invalidate()
    }


}