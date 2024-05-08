package com.ahuynh.muzimusicapp.ui.component.setting

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentSettingBinding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.chart.ChartFragment
import com.ahuynh.muzimusicapp.ui.component.song.SongViewModel
import com.ahuynh.muzimusicapp.ui.upload.UploadActivity
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {
    private val viewModel by viewModels<SongViewModel>({ requireActivity() })
    private var listSong: ArrayList<Song> = arrayListOf()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listenSongList.observe(viewLifecycleOwner) {
            listSong = it as ArrayList<Song>
        }
        binding.btnCsv.setOnClickListener {
            exportToCSVByDay()
            exportCsvByMonth()

        }

        binding.btnPdf.setOnClickListener {
            exportToPDFByDay()
            exportToPDFByMonth()
        }

        binding.btnUpload.setOnClickListener {
            startActivity(Intent(requireContext(), UploadActivity::class.java))
        }

    }


    private fun exportToCSV(data: MutableList<List<String>>, fileName: String) {
        val folderName = "MuziMusic"
        val dcimDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        val muziMusicDirectory = File(dcimDirectory, folderName)

        if (!muziMusicDirectory.exists()) {
            if (!muziMusicDirectory.mkdirs()) {
                Log.e(ChartFragment.TAG, "Failed to create MuziMusic directory")
                return
            }
        }

        val csvFile = File(muziMusicDirectory, fileName)

        try {
            val fileOutputStream = FileOutputStream(csvFile)
            val outputStreamWriter = OutputStreamWriter(fileOutputStream)

            // Write data to CSV
            data.forEach { row ->
                val csvRow = row.joinToString(",") + "\n"
                outputStreamWriter.write(csvRow)
            }

            outputStreamWriter.close()
            fileOutputStream.close()

            Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e(ChartFragment.TAG, "Error saving CSV file: ${e.message}")
        }
    }

    private fun exportToCSVByDay() {
        val data = mutableListOf<List<String>>()

        listSong.forEach { song ->
            data.add(listOf(song.name, song.listen) as List<String>)
        }

        exportToCSV(data, "data_by_day.csv")
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
                        i.value
                    ) as List<String>
                )
        }

        exportToCSV(data, "data_by_month.csv")
    }

    private fun exportToPDF(data: MutableList<List<String>>, fileName: String) {
        val folderName = "MuziMusic"
        val dcimDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val muziMusicDirectory = File(dcimDirectory, folderName)

        if (!muziMusicDirectory.exists()) {
            if (!muziMusicDirectory.mkdirs()) {
                Log.e(ChartFragment.TAG, "Failed to create MuziMusic directory")
                return
            }
        }

        val pdfFile = File(muziMusicDirectory, fileName)

        try {
            val writer = PdfWriter(FileOutputStream(pdfFile))
            val pdf = com.itextpdf.kernel.pdf.PdfDocument(writer)
            val document = Document(pdf)


            // Write data to PDF
            data.forEach { row ->
                val paragraph = Paragraph(row.joinToString(","))
                document.add(paragraph)
            }

            document.close()

            Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e(ChartFragment.TAG, "Error saving PDF file: ${e.message}")
        }
    }

    private fun exportToPDFByDay() {
        val data = mutableListOf<List<String>>()

        listSong.forEach { song ->
            data.add(listOf(song.name, song.listen) as List<String>)
        }

        exportToPDF(data, "data_by_day.pdf")
    }

    private fun exportToPDFByMonth() {
        val data = mutableListOf<List<String>>()

        listSong.forEach { song ->
            for (i in song.listens)
                data.add(listOf(song.name, song.listen, i.key, i.value) as List<String>)
        }

        exportToPDF(data, "data_by_month.pdf")
    }


}

