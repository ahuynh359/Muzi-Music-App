package com.ahuynh.muzimusicapp.ui.component.user.chart

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.ChartAdapter
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.response.ListenOfDayResponse
import com.ahuynh.muzimusicapp.databinding.FragmentChartBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.ui.component.user.song.menu.SongMenu
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.makeramen.roundedimageview.RoundedImageView
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Date
import java.util.Random
import kotlin.math.abs
import kotlin.math.min

@AndroidEntryPoint
class ChartFragment : BaseFragment<FragmentChartBinding>(FragmentChartBinding::inflate),
    ChartAdapter.OnChartClicked {

    private val viewModel by viewModels<ChartViewModel>()
    private lateinit var songAdapter: ChartAdapter
    private val values: ArrayList<ArrayList<Entry>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getChartList()
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerSong.startShimmer()
    }

    override fun onPause() {
        super.onPause()
        binding.shimmerSong.stopShimmer()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSwipeRefresh()
        setupRecyclerView()

        viewModel.top3List.observe(viewLifecycleOwner) {
            values.clear()
            for (topIndex in 0 until min(3, it.size)) {
                values.add(ArrayList())
                it[topIndex].listenDetail?.let { listListen ->
                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.DAY_OF_MONTH, -10)
                    for (cnt in 0 until 10) {
                        calendar.add(Calendar.DAY_OF_MONTH, 1)
                        val numListen = getListenOfDay(calendar.time, listListen)
                        values[topIndex].add(Entry(cnt.toFloat(), numListen.toFloat()))
                    }
                }
            }

            setupChart()
        }

        viewModel.chartList.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = false
            binding.shimmerSong.stopShimmer()
            binding.shimmerSong.visibility = View.GONE
            binding.rcySong.visibility = View.VISIBLE
            songAdapter.submitList(it)
            viewModel.getTopSongDrawable(requireContext())
        }

        viewModel.songDrawables.observe(viewLifecycleOwner) {
            binding.lineChart.data = generateDataLine()
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getChartList()
        }
    }

    private fun setupRecyclerView() {
        songAdapter = ChartAdapter(this)
        binding.rcySong.adapter = songAdapter
    }




    private fun getListenOfDay(checkDate: Date, listens: List<ListenOfDayResponse>): Int {
        for (listenOfDay in listens) {
            val date = Utils.stringToDate(listenOfDay.day)
            val calendar1 = Calendar.getInstance()
            val calendar2 = Calendar.getInstance()

            calendar1.time = checkDate
            date?.let {
                calendar2.time = it
                if (calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
                    && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                ) return listenOfDay.listen
            }
        }
        return 0
    }

    private fun setupChart() {
        binding.lineChart.apply {
            description.isEnabled = false
            setDrawGridBackground(false)
            isDragEnabled = false
            setScaleEnabled(false)
            setPinchZoom(false)
            legend.isEnabled = false

            xAxis.apply {
                labelCount = 9
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                textColor = ContextCompat.getColor(context, R.color.white)
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        val calendar = Calendar.getInstance()
                        calendar.add(Calendar.DAY_OF_MONTH, value.toInt() - 9)
                        return "${calendar.get(Calendar.DAY_OF_MONTH)}"
                    }
                }
            }
            axisLeft.apply {
                isEnabled = false
            }
            axisRight.apply {
                labelCount = 4
                setDrawGridLines(false)
                textColor = ContextCompat.getColor(context, R.color.white)
            }

            animateY(1500)
            data = generateDataLine()

            setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    binding.lineChart.data = generateDataLine(h?.dataSetIndex ?: -1)
                }

                override fun onNothingSelected() {

                }
            })
        }
    }


    private fun generateDataLine(whiteSetIndex: Int = -1): LineData {

        val sets: ArrayList<ILineDataSet> = ArrayList()
        val dataSets: ArrayList<LineDataSet> = ArrayList()

        for (i in 0 until values.size) {
            var lineDataSet: LineDataSet
            if (whiteSetIndex == i) {
                val whiteValue = values[i].toMutableList()
                val randomIndex = abs(Random().nextInt() % 9 + 1)

                val a = RoundedImageView(context)
                a.setImageDrawable(viewModel.songDrawables.value?.get(i))
                a.cornerRadius = 24f
                a.borderWidth = Utils.convertDpToPixel(2f, requireContext()).toFloat()
                a.borderColor = Constants.colorsTopSong[i]

                whiteValue[randomIndex] = Entry(
                    randomIndex.toFloat(),
                    whiteValue[randomIndex].y,
                    a.drawable
                )

                lineDataSet = LineDataSet(whiteValue, "Top ${i + 1}").apply {
                    setDrawCircles(true)
                    setDrawCircleHole(true)
                    circleRadius = 3.5f
                    setCircleColor(Constants.colorsTopSong[i])
                }
            } else {
                lineDataSet = LineDataSet(values[i], "Top ${i + 1}").apply {
                    setDrawCircles(false)
                    setDrawCircleHole(false)
                }
            }

            lineDataSet.apply {
                lineWidth = 1.5f
                color = Constants.colorsTopSong[i]
                setDrawValues(false)
            }
            dataSets.add(lineDataSet)
            sets.add(dataSets[i])
        }

        return LineData(sets)
    }

    override fun onSongClicked(song: Song) {
        startActivity(Intent(context, PlayerActivity::class.java))
        Utils.sendMusic(
            requireContext(),
            MusicService.ACTION_PLAY,
            song,
            viewModel.chartList.value as ArrayList<Song>
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
