package com.ahuynh.muzimusicapp.ui.component.player

import android.content.Intent
import android.media.audiofx.AudioEffect
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.ViewPagerAdapter
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.ActivityPlayerBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.BaseActivity
import com.ahuynh.muzimusicapp.ui.component.player.lyrics.LyricsFragment
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.EventBusModel
import com.ahuynh.muzimusicapp.utils.Utils.toTimeFormat
import com.ahuynh.muzimusicapp.utils.helper.VersionHelper
import com.google.android.material.slider.Slider
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.system.exitProcess


@AndroidEntryPoint
class PlayerActivity : BaseActivity<ActivityPlayerBinding>(ActivityPlayerBinding::inflate) {
    companion object {
        const val TAG = "PlayerActivityABC"
    }
    private val sleepTimerDialog : SleepTimerDialog by lazy {
        SleepTimerDialog()
    }

    private val viewModel by viewModels<PlayerViewModel>()
    private var isSliderPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        handleUI()
        EventBus.getDefault().post(EventBusModel.RequestSongEvent())

        observe()


    }

    private fun getData() {

    }

    private fun observe() {
        viewModel.getShuffle()
        viewModel.getRepeat()
        viewModel.audioSessionId.observe(this) {
            if (it != 0) {
                binding.visualizer.setColor(ContextCompat.getColor(this, R.color.white))
                binding.visualizer.setPlayer(it)
            }
        }


        viewModel.isShuffle.observe(this) {
            if (it) {
                binding.btnShuffle.setImageResource(com.ahuynh.muzimusicapp.R.drawable.ic_shuffle_selected)
            } else{
                binding.btnShuffle.setImageResource(com.ahuynh.muzimusicapp.R.drawable.ic_shuffle)
            }

        }

        viewModel.isRepeat.observe(this) {
            if (it) {
                binding.btnRepeat.setImageResource(com.ahuynh.muzimusicapp.R.drawable.ic_repeat_selected)
            } else{
                binding.btnRepeat.setImageResource(com.ahuynh.muzimusicapp.R.drawable.ic_repeat)
            }

        }

        viewModel.isPlaying.observe(this) {
            binding.btnPlayPause.setImageResource(
                if (it) R.drawable.ic_play
                else R.drawable.ic_pause
            )
        }

        viewModel.song.observe(this) { song ->
            if(song.types.isNotEmpty()){
                var str = ""
                for (i in song.types) {
                    str += "$i, "
                }
                str = str.substring(0, str.length - 2)
                binding.tvType.text = str
            }


            viewModel.isUserLoveSong(song.id)
            binding.btnHeart.setOnClickListener {
                //viewModel.loveOrUnlove(song.id)

            }
        }
        viewModel.loveSong.observe(this) {
            if (it) {
                binding.btnHeart.setImageResource(R.drawable.ic_hearted)
            } else binding.btnHeart.setImageResource(R.drawable.ic_heart_small)
        }


        viewModel.sleepTime.observe(this){
            binding.tvTimer.text = it
            if(it.equals("00:00:00")){
                finishAffinity()
                exitProcess(0)

            }
        }


    }


    private fun handleUI() {

        setUpViewPager()
        setUpSeekbar()

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
            binding.viewPager.currentItem = 0
            viewModel.currentSongTime.postValue(0)
            binding.slider.value = 0f
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
            binding.viewPager.currentItem = 0
            viewModel.currentSongTime.postValue(0)
            binding.slider.value = 0f
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
            if(!sleepTimerDialog.isAdded){
                sleepTimerDialog.show(supportFragmentManager,null)
            }
        }

        binding.btnHeadphone.setOnClickListener {
            Intent(AudioEffect.ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL).apply {
                putExtra(AudioEffect.EXTRA_AUDIO_SESSION,viewModel.audioSessionId.value)
                putExtra(AudioEffect.EXTRA_PACKAGE_NAME,packageName)
                putExtra(AudioEffect.EXTRA_CONTENT_TYPE,AudioEffect.EXTRA_CONTENT_TYPE)
            }.also {
                startActivityForResult(it,133)
            }
        }


    }

    private fun setUpViewPager() {
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val fragmentList: ArrayList<Fragment> = arrayListOf(SongMainFragment(), LyricsFragment())
        binding.viewPager.adapter = ViewPagerAdapter(fragmentList, this)
        binding.viewPager.currentItem = 0
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                dotIndicator(position)
            }
        })
    }

    private fun dotIndicator(position: Int) {
        when (position) {
            0 -> {
                binding.dot1.setImageResource(com.ahuynh.muzimusicapp.R.drawable.dot_selected)
                binding.dot2.setImageResource(com.ahuynh.muzimusicapp.R.drawable.dot_default)
            }

            1 -> {
                binding.dot2.setImageResource(com.ahuynh.muzimusicapp.R.drawable.dot_selected)
                binding.dot1.setImageResource(com.ahuynh.muzimusicapp.R.drawable.dot_default)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
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


    override fun getSnackbarView(): View {
        return binding.main
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    fun onAudioSessionIdEvent(event: EventBusModel.AudioSessionIdEvent) {
        viewModel.audioSessionId.postValue(event.sessionId)


    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    fun onSongInfo(event: EventBusModel.SongInfoEvent) {
        event.song?.let {
            viewModel.song.postValue(it)
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
            viewModel.currentSongTime.postValue(event.timeMillis.toInt())
        } else {
            binding.shimmerSlider.visibility = View.VISIBLE
            binding.slider.visibility = View.INVISIBLE
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
        viewModel.currentSongTime.postValue(0)

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
                viewModel.currentSongTime.postValue(binding.slider.value.toInt())

            }

        })
    }


}