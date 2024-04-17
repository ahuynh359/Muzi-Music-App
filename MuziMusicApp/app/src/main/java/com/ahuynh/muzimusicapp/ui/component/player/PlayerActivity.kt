package com.ahuynh.muzimusicapp.ui.component.player

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.LyricAdapter
import com.ahuynh.muzimusicapp.adapter.LyricsClickListener
import com.ahuynh.muzimusicapp.data.model.Lyric
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.ActivityPlayerBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.component.playlist.PlaylistAddDialog
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.EventBusModel
import com.ahuynh.muzimusicapp.utils.NetworkConnectivityHelper
import com.ahuynh.muzimusicapp.utils.Utils
import com.ahuynh.muzimusicapp.utils.Utils.convertStringToLyric
import com.ahuynh.muzimusicapp.utils.Utils.toTimeFormat
import com.ahuynh.muzimusicapp.utils.VersionHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.system.exitProcess


@AndroidEntryPoint
class PlayerActivity : AppCompatActivity(), LyricsClickListener {
    companion object {
        const val TAG = "PlayerActivityABC"
    }
    private lateinit var snackbar: Snackbar
    private lateinit var binding : ActivityPlayerBinding
    private val networkConnectivityObserver: NetworkConnectivityHelper by lazy {
        NetworkConnectivityHelper(this)
    }
    private val sleepTimerDialog: SleepTimerDialog by lazy {
        SleepTimerDialog()
    }
    private val viewModel by viewModels<PlayerViewModel>()
    private var isSliderPressed = false
    private lateinit var playerAdapter: LyricAdapter
    private var songLyrics: ArrayList<Lyric> = arrayListOf()
    private lateinit var centerLayoutManager: CenterLayoutManager
    private var currentLine = -1
    private var scrollJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        centerLayoutManager = CenterLayoutManager(this)
        playerAdapter = LyricAdapter(songLyrics, this, this)

        handleUI()
        EventBus.getDefault().post(EventBusModel.RequestSongEvent())
        setUpSeekbar()
        observe()


    }

    private fun observe() {
        viewModel.getShuffle()
        viewModel.getRepeat()

        Log.d("ABC",viewModel.isShuffle.value.toString())

        viewModel.isShuffle.observe(this) {
            Log.d("ABC aaa",it.toString())
            if (it) {
                binding.btnShuffle.setImageResource(R.drawable.ic_shuffle_selected)
            } else{
                binding.btnShuffle.setImageResource(R.drawable.ic_shuffle)
            }

        }

        viewModel.isRepeat.observe(this) {
            if (it) {
                binding.btnRepeat.setImageResource(R.drawable.ic_repeat_selected)
            } else{
                binding.btnRepeat.setImageResource(R.drawable.ic_repeat)
            }

        }

        viewModel.isPlaying.observe(this) {
            binding.btnPlayPause.setImageResource(
                if (it) R.drawable.ic_play
                else R.drawable.ic_pause
            )
        }

        viewModel.song.observe(this) { song ->
            Log.d(TAG, getSongLyrics(song.lyrics!!).toString())
            playerAdapter.setData(getSongLyrics(song.lyrics))
            songLyrics = getSongLyrics(song.lyrics)

        }

        viewModel.sleepTime.observe(this){
            binding.tvTimer.text = it
            if(it.equals("00:00:00")){
                finishAffinity();
                exitProcess(0)
            }
        }
        viewModel.currentSongTime.observe(this) { time ->
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
            binding.tvLyrics.visibility = View.GONE
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
                binding.tvLyrics.text = songLyrics[indexLine].text
                binding.tvLyrics.visibility = View.VISIBLE
            } else {
                binding.rcyLyrics.smoothScrollToPosition(indexLine)
                binding.tvLyrics.visibility = View.GONE
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
        if(text.isEmpty()){
            lyrics.add(Lyric(0,"No lyrics"))
        } else {
            val list = text.split("\\n").map { it.trimEnd('\\') }
            for (line in list) {
                lyrics.add(line.convertStringToLyric())
            }
        }

        return lyrics
    }

    private fun handleUI() {
        binding.rcyLyrics.adapter = playerAdapter
        binding.rcyLyrics.layoutManager = centerLayoutManager
        binding.btnShuffle.setOnClickListener {
            val value = viewModel.isShuffle.value ?: false
            viewModel.setShuffle(!value)
        }

        binding.btnRepeat.setOnClickListener {
            val value = viewModel.isRepeat.value ?: false
            viewModel.setRepeat(!value)
        }
        binding.btnPlayPause.setOnClickListener {

            if (viewModel.isClear) {
                sendMusic(
                    MusicService.ACTION_PLAY,
                    viewModel.song.value,
                    viewModel.songList.value!!
                )
                viewModel.isClear = false
            } else {
                sendMusic(MusicService.ACTION_PLAY)
            }
        }
        binding.btnPre.setOnClickListener {
            if (viewModel.isClear) {
                sendMusic(
                    MusicService.ACTION_PRE,
                    viewModel.song.value,
                    viewModel.songList.value!!
                )
                viewModel.isClear = false
            } else {
                sendMusic(MusicService.ACTION_PRE)
            }
        }
        binding.btnDown.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnNext.setOnClickListener {
            if (viewModel.isClear) {
                sendMusic(
                    MusicService.ACTION_NEXT,
                    viewModel.song.value,
                    viewModel.songList.value!!
                )
                viewModel.isClear = false
            } else {
                sendMusic(MusicService.ACTION_NEXT)
            }
        }

        binding.btnSleep.setOnClickListener {
            if (!sleepTimerDialog.isAdded) {
                sleepTimerDialog.show(supportFragmentManager, PlaylistAddDialog.TAG)
            }
        }
    }

    fun sendMusic(
        action: Int,
        song: Song? = null,
        songList: ArrayList<Song> = arrayListOf()
    ) {

        val bundle = Bundle().apply {
            putParcelable(Constants.SONG, song)
            putParcelableArrayList(Constants.SONG_LIST, songList)
        }

        val intent = Intent(applicationContext, MusicService::class.java).apply {
            putExtra(Constants.ACTION, action)
            putExtra(Constants.DATA, bundle)
        }

        if (VersionHelper.isO()) {
            this.startForegroundService(intent)
        } else {
            this.startService(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)

        snackbar = Snackbar.make(
            binding.main,
            "No Internet Connection",
            Snackbar.LENGTH_INDEFINITE
        ).setAction("Wifi") {
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }
        networkConnectivityObserver.observe(this) {
            when (it) {
                true -> {
                    if (snackbar.isShown) {
                        snackbar.dismiss()
                    }
                }

                else -> {
                    snackbar.show()
                }
            }

        }

    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    fun onAudioSessionIdEvent(event: EventBusModel.AudioSessionIdEvent) {
    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    fun onSongInfo(event: EventBusModel.SongInfoEvent) {
        event.song?.let { song ->
            viewModel.song.postValue(song)

            runOnUiThread {
                binding.tvSong.text = song.name
                binding.tvSongName.text = song.name
                binding.tvSinger.text = song.singer

                Glide
                    .with(binding.imvSong.context)
                    .load(song.image)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(com.ahuynh.muzimusicapp.R.drawable.big_song)
                    .into(binding.imvSong);
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    fun onMusicPlayingEvent(event: EventBusModel.MusicPlayingEvent) {
        viewModel.isPlaying.postValue(event.isPlaying)

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onMusicTimeEvent(event: EventBusModel.MusicTimeEvent) {
        if (event.duration > 0) {
            binding.shimmerSlider.stopShimmer()
            binding.slider.visibility = View.VISIBLE
            binding.shimmerSlider.visibility = View.GONE
            binding.tvEndTime.text = (event.duration / 1000).toInt().toTimeFormat()
            if (!isSliderPressed) binding.slider.value = event.timeMillis.toFloat()
            binding.slider.valueTo = event.duration.toFloat()
            binding.tvStartTime.text = (event.timeMillis / 1000).toInt().toTimeFormat()
        } else {
            binding.shimmerSlider.startShimmer()
            binding.tvEndTime.text = "N:/N"
            binding.tvStartTime.text = "00:00"
        }





    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onClearMusic(event: EventBusModel.ClearMusic) {
        viewModel.isClear = true
        viewModel.isPlaying.postValue(false)
        binding.slider.value = 0f


    }
    private fun setUpSeekbar() {
        binding.slider.setLabelFormatter { value: Float ->
            (value / 1000).toInt().toTimeFormat()
        }

        binding.slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(p0: Slider) {
                isSliderPressed = true
            }

            override fun onStopTrackingTouch(p0: Slider) {
                EventBus.getDefault()
                    .post(EventBusModel.MusicTimeSeekEvent(binding.slider.value.toLong()))
                isSliderPressed = false
                viewModel.isUserTouchSlider = true

            }

        })
    }


    override fun onLineLyricsClick(line: Lyric) {
        EventBus.getDefault().post(EventBusModel.MusicTimeSeekEvent(line.startTime.toLong()))
        if (viewModel.isPlaying.value == false) {
            Intent(this, MusicService::class.java).apply {
                putExtra(Constants.ACTION, MusicService.ACTION_PLAY)
            }.also {
                Utils.startMusic(this, it)
            }
        }
    }


}