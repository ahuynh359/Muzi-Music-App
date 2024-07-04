package com.ahuynh.muzimusicapp.ui.component.player

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.adapter.LyricAdapter
import com.ahuynh.muzimusicapp.adapter.LyricsClickListener
import com.ahuynh.muzimusicapp.data.model.Lyric
import com.ahuynh.muzimusicapp.databinding.FragmentLyricsBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.EventBusModel
import com.ahuynh.muzimusicapp.utils.Utils
import com.ahuynh.muzimusicapp.utils.Utils.convertStringToLyric
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

@AndroidEntryPoint
class LyricsFragment : BaseFragment<FragmentLyricsBinding>(FragmentLyricsBinding::inflate),
    LyricsClickListener {
    private val viewModel by viewModels<PlayerViewModel>({requireActivity()})
    private lateinit var playerAdapter: LyricAdapter
    private lateinit var centerLayoutManager: CenterLayoutManager
    private var songLyrics: ArrayList<Lyric> = arrayListOf()
    private var currentLine = -1
    private var scrollJob: Job? = null

    companion object {
        const val TAG = "LyricsFragment"
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerAdapter = LyricAdapter(songLyrics, requireContext(), this)
        centerLayoutManager = CenterLayoutManager(context)

        handleUI()
        observeData()
    }

    private fun handleUI() {
        binding.rcyLyrics.adapter = playerAdapter
        binding.rcyLyrics.layoutManager = centerLayoutManager
    }

    private fun observeData() {
        viewModel.song.observe(requireActivity()) { song ->
            playerAdapter.setData(getSongLyrics(song.lyrics))
            songLyrics = getSongLyrics(song.lyrics)

        }
        viewModel.currentSongTime.observe(viewLifecycleOwner) { time ->
            if(time == 0){
                currentLine = -1
                playerAdapter.resetCurrent()
                smartScrollLyrics(0)
            }
            binding.rcyLyrics.post {
                if (viewModel.isUserTouchSlider) {
                    scrollLyrics(time)
                } else
                    smartScrollLyrics(time)
            }


        }


    }
    private fun scrollLyrics(time: Int) {
        val indexLine = indexLine(time, songLyrics)

        if (indexLine != currentLine && indexLine >= 0 && indexLine < songLyrics.size) {
            playerAdapter.currentLine(indexLine)

            binding.rcyLyrics.smoothScrollToPosition(indexLine)
            binding.tvLyric.visibility = View.GONE
            currentLine = indexLine
            if (scrollJob?.isActive == true) scrollJob?.cancel()
            scrollJob = MainScope().launch {
                delay(1000)
                viewModel.isUserTouchSlider = false
                cancel()
            }
            scrollJob?.start()
        }
    }

    private fun smartScrollLyrics(time: Int) {
        val indexLine = indexLine(time, songLyrics)

        if (indexLine != currentLine && indexLine >= 0 && indexLine < songLyrics.size) {
            playerAdapter.currentLine(indexLine)
            if (indexLine < centerLayoutManager.findFirstVisibleItemPosition() || indexLine > centerLayoutManager.findLastVisibleItemPosition()) {
                binding.tvLyric.text = songLyrics[indexLine].text
                binding.tvLyric.visibility = View.VISIBLE
            } else {
                binding.rcyLyrics.smoothScrollToPosition(indexLine)
                binding.tvLyric.visibility = View.GONE
            }
            currentLine = indexLine
        }
    }
    //Find position of right lyrics with currentTime
    private fun indexLine(time: Int, lyrics: ArrayList<Lyric>): Int {
        var left = 0
        var right = lyrics.size - 1

        while (left <= right) {
            val middle = (left + right) / 2
            if (time < lyrics[middle].startTime) {
                right = middle - 1

            } else {
                if (middle < lyrics.size - 1) {
                    if (time < lyrics[middle + 1].startTime) {
                        return middle
                    } else {
                        left = middle + 1
                    }
                } else {
                    return middle
                }
            }
        }
        return -1
    }

    private fun getSongLyrics(text : String) : ArrayList<Lyric>{
        val lyrics = arrayListOf<Lyric>()
        if(text.isBlank()){
            lyrics.add(Lyric(0,"No lyrics"))
        } else {
            val list = text.split("\\n").map { it.trimEnd('\\') }
            for (line in list) {
                lyrics.add(line.convertStringToLyric())
            }
        }

        return lyrics
    }
    override fun onLineLyricsClick(line: Lyric) {
        EventBus.getDefault().post(EventBusModel.MusicTimeSeekEvent(line.startTime.toLong()))
        if (viewModel.isPlaying.value == false) {
            Intent(requireContext(), MusicService::class.java).apply {
                putExtra(Constants.ACTION, MusicService.ACTION_PLAY)
            }.also {
                Utils.startMusic(requireContext(), it)
            }
        }
    }
}