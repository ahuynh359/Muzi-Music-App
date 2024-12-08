package com.ahuynh.muzimusicapp.ui.component.admin.dashboard

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.MenuAdapter
import com.ahuynh.muzimusicapp.data.model.ItemMenu
import com.ahuynh.muzimusicapp.data.model.ItemMenuName
import com.ahuynh.muzimusicapp.data.model.response.ListenOfDayResponse
import com.ahuynh.muzimusicapp.databinding.FragmentDashboardBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
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
class DashboardFragment :
    BaseFragment<FragmentDashboardBinding>(FragmentDashboardBinding::inflate), MenuAdapter.OnItemMenuAdapterClicked  {
    private val values: ArrayList<ArrayList<Entry>> = ArrayList()
    private val settingList = ArrayList<ItemMenu>()
    private val settingAdapter = MenuAdapter(this)
    private val viewModel by viewModels<DashboardViewModel>()

    companion object {
        const val TAG = "DashboardFragment"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observe()


    }

    private fun observe() {
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
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        viewModel.getAllUsers()
        viewModel.getChartList()
        viewModel.getTotalResponse()
        //viewModel.getTopSongDrawable(requireContext())
    }


    private fun handleUI() {
        binding.rcySetting.adapter = settingAdapter
        handleUserData()
        handleSongData()
        handleTotalResponse()


    }

    private fun handleTotalResponse() {
        viewModel.totalResponse.observe(viewLifecycleOwner) {
            Log.d("ABC",it.data.toString())
            settingList.add(
                ItemMenu(
                    it.data.totalUser,
                    R.drawable.ic_user,
                    ItemMenuName.LANGUAGE
                )
            )
            settingList.add(
                ItemMenu(
                    it.data.totalSong,
                    R.drawable.ic_song,
                    ItemMenuName.LANGUAGE
                )
            )
            settingList.add(
                ItemMenu(
                    it.data.totalAlbum,
                    R.drawable.ic_album,
                    ItemMenuName.LANGUAGE
                )
            )
            settingList.add(
                ItemMenu(
                    it.data.totalSinger,
                    R.drawable.ic_user,
                    ItemMenuName.LANGUAGE
                )
            )
            settingList.add(
                ItemMenu(
                    it.data.totalType,
                    R.drawable.ic_type,
                    ItemMenuName.LANGUAGE
                )
            )
            settingAdapter.submitList(settingList)
        }
    }

    private fun handleSongData() {
        viewModel.top3List.observe(viewLifecycleOwner) {
            values.clear()
            for (topIndex in 0 until min(3, it.size)) {
                values.add(ArrayList())
                it[topIndex].listenDetail.let { listListen ->
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
        viewModel.songDrawables.observe(viewLifecycleOwner) {
            binding.lineChart.data = generateDataLine()
        }
    }

    private fun handleUserData() {
        viewModel.userList.observe(viewLifecycleOwner) {
            val lockedUsers = (it.count { it.locked }).toFloat()

            val unlockedUsers = (it.size - lockedUsers).toFloat()

            val entries = ArrayList<PieEntry>()
            entries.add(PieEntry(lockedUsers, "Locked "))
            entries.add(PieEntry(unlockedUsers, "Unlocked"))

            val dataSet = PieDataSet(entries, "User Account Status")
            dataSet.colors = listOf(Color.rgb(255, 153, 153), Color.rgb(102, 179, 255))

            val data = PieData(dataSet)
            data.setValueTextSize(12f)
            data.setValueTextColor(Color.WHITE)

            binding.userLockChart.apply {
                setEntryLabelColor(Color.WHITE)
                this.data = data
                description.isEnabled = false
                centerText = "User Status"
                animateY(1000)

                legend.isEnabled = false
            }
        }
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
            data = generateDataLine() // Sử dụng dữ liệu Bar

            setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    binding.lineChart.data = generateDataLine(h?.dataSetIndex ?: -1)
                }

                override fun onNothingSelected() {
                    // Xử lý khi không có giá trị nào được chọn
                }
            })
        }
    }
    private fun generateDataLine(whiteSetIndex: Int = -1): LineData {
        val sets = ArrayList<ILineDataSet>()
        val dataSets = ArrayList<LineDataSet>()

        for (i in 0 until values.size) {
            val lineDataSet: LineDataSet
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
            sets.add(lineDataSet) // Sửa lỗi ở đây, thay vì thêm `dataSets[i]`, ta nên thêm `lineDataSet`.
        }

        return LineData(sets)
    }

    override fun onMenuClicked(menu: ItemMenu) {

    }


}
