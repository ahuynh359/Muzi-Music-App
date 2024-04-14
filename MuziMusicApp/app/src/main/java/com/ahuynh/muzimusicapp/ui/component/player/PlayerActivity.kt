package com.ahuynh.muzimusicapp.ui.component.player

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.OnLyricsClicked
import com.ahuynh.muzimusicapp.adapter.PlayerAdapter
import com.ahuynh.muzimusicapp.data.model.Lyric
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.ActivityPlayerBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.component.playlist.PlaylistAddDialog
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Constants.ACTION_NEXT
import com.ahuynh.muzimusicapp.utils.Constants.ACTION_PLAY
import com.ahuynh.muzimusicapp.utils.Constants.ACTION_PRE
import com.ahuynh.muzimusicapp.utils.EventBusModel
import com.ahuynh.muzimusicapp.utils.NetworkConnectivityHelper
import com.ahuynh.muzimusicapp.utils.Utils.convertStringToLyric
import com.ahuynh.muzimusicapp.utils.Utils.toTimeFormat
import com.ahuynh.muzimusicapp.utils.VersionHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.system.exitProcess


@AndroidEntryPoint
class PlayerActivity : AppCompatActivity(), OnLyricsClicked {
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
    private var playerAdapter: PlayerAdapter = PlayerAdapter(this)
    private var songLyrics: ArrayList<Lyric> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleUI()
        EventBus.getDefault().post(EventBusModel.RequestSongEvent())
        setUpSeekbar()
        observe()


    }

    private fun observe() {
        viewModel.getShuffle()
        viewModel.getRepeat()

        viewModel.isShuffle.observe(this) {
            if (it) {
                binding.btnShuffle.setImageResource(R.drawable.ic_shuffle_selected)
            } else
                binding.btnShuffle.setImageResource(R.drawable.ic_shuffle)
        }

        viewModel.isRepeat.observe(this) {
            if (it) {
                binding.btnRepeat.setImageResource(R.drawable.ic_repeat_selected)
            } else
                binding.btnRepeat.setImageResource(R.drawable.ic_repeat)
        }

        viewModel.isPlaying.observe(this) {
            binding.btnPlayPause.setImageResource(
                if (it) R.drawable.ic_play
                else R.drawable.ic_pause
            )
        }

        viewModel.song.observe(this) { song ->
            Log.d(TAG, getSongLyrics(song.lyrics!!).toString())
            playerAdapter.submitList(getSongLyrics(song.lyrics))
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
                smartScrollLyrics(time)
            }

        }

    }

    private fun smartScrollLyrics(time: Int) {
        val indexLine = indexLine(time, songLyrics)
    }

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
                    ACTION_PLAY,
                    viewModel.song.value,
                    viewModel.songList.value!!
                )
                viewModel.isClear = false
            } else {
                sendMusic(ACTION_PLAY)
            }
        }
        binding.btnPre.setOnClickListener {
            if (viewModel.isClear) {
                sendMusic(
                    ACTION_PRE,
                    viewModel.song.value,
                    viewModel.songList.value!!
                )
                viewModel.isClear = false
            } else {
                sendMusic(ACTION_PRE)
            }
        }
        binding.btnDown.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnNext.setOnClickListener {
            if (viewModel.isClear) {
                sendMusic(
                    ACTION_NEXT,
                    viewModel.song.value,
                    viewModel.songList.value!!
                )
                viewModel.isClear = false
            } else {
                sendMusic(ACTION_NEXT)
            }
        }
        binding.rcyLyrics.adapter = playerAdapter
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

            }

        })
    }

    override fun onLyricsClicked(lyric: Lyric) {
    }


}