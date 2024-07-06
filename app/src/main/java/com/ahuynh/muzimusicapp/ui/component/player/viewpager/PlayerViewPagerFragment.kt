package com.ahuynh.muzimusicapp.ui.component.player.viewpager

import android.content.Intent
import android.media.audiofx.AudioEffect
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.PlayerViewPagerAdapter
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentPlayerViewPagerBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerViewModel
import com.ahuynh.muzimusicapp.ui.component.player.sleep.SleepTimerDialog
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.EventBusModel
import com.ahuynh.muzimusicapp.utils.Utils.toTimeFormat
import com.ahuynh.muzimusicapp.utils.helper.VersionHelper
import com.google.android.material.slider.Slider
import org.greenrobot.eventbus.EventBus
import kotlin.system.exitProcess

class PlayerViewPagerFragment :
    BaseFragment<FragmentPlayerViewPagerBinding>(FragmentPlayerViewPagerBinding::inflate) {
    var isSliderPressed: Boolean = false

    private val sleepTimerDialog: SleepTimerDialog by lazy {
        SleepTimerDialog()
    }

    private val viewModel by viewModels<PlayerViewModel>({ requireActivity() })
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
        observe()

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
        val intent = Intent(requireContext(), MusicService::class.java).apply {
            putExtra(Constants.ACTION, action)
            putExtra(Constants.DATA, bundle)
        }

        if (VersionHelper.isO()) {
            requireContext().startForegroundService(intent)
        } else {
            requireContext().startService(intent)
        }

    }


    private fun observe() {
        viewModel.getShuffle()
        viewModel.getRepeat()
        viewModel.audioSessionId.observe(viewLifecycleOwner) {
            if (it != 0) {
                binding.visualizer.setColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding.visualizer.setPlayer(it)
            }
        }


        viewModel.isShuffle.observe(viewLifecycleOwner) {
            if (it) {
                binding.btnShuffle.setImageResource(R.drawable.ic_shuffle_selected)
            } else {
                binding.btnShuffle.setImageResource(R.drawable.ic_shuffle)
            }

        }

        viewModel.isRepeat.observe(viewLifecycleOwner) {
            if (it) {
                binding.btnRepeat.setImageResource(R.drawable.ic_repeat_selected)
            } else {
                binding.btnRepeat.setImageResource(R.drawable.ic_repeat)
            }

        }

        viewModel.isPlaying.observe(viewLifecycleOwner) {
            binding.btnPlayPause.setImageResource(
                if (it) R.drawable.ic_play
                else R.drawable.ic_pause
            )
        }

        viewModel.song.observe(viewLifecycleOwner) { song ->


            viewModel.isUserLoveSong(song.id)
            binding.btnHeart.setOnClickListener {
                viewModel.loveOrUnlove(song.id)

            }
        }
        viewModel.loveSong.observe(viewLifecycleOwner) {
            if (it) {
                binding.btnHeart.setImageResource(R.drawable.ic_hearted)
            } else binding.btnHeart.setImageResource(R.drawable.ic_heart_small)
        }


        viewModel.sleepTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
            if (it.equals("00:00:00")) {
                requireActivity().finishAffinity()
                exitProcess(0)

            }
        }

        viewModel.duration.observe(viewLifecycleOwner) { d ->
            viewModel.timeMillis.observe(viewLifecycleOwner) { t ->
                if (d > 0) {
                    binding.shimmerSlider.stopShimmer()
                    binding.slider.visibility = View.VISIBLE
                    binding.shimmerSlider.visibility = View.GONE
                    binding.tvEndTime.text = (d / 1000).toInt().toTimeFormat()
                    if (!isSliderPressed) binding.slider.value = t.toFloat()
                    binding.slider.valueTo = d.toFloat()
                    binding.tvStartTime.text = (t / 1000).toInt().toTimeFormat()
                    viewModel.currentSongTime.postValue(t.toInt())
                } else {
                    binding.shimmerSlider.visibility = View.VISIBLE
                    binding.slider.visibility = View.INVISIBLE
                    binding.shimmerSlider.startShimmer()
                    binding.tvEndTime.text = "N:/N"
                    binding.tvStartTime.text = "00:00"
                }
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
            requireActivity().finish()

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
            if (!sleepTimerDialog.isAdded) {
                sleepTimerDialog.show(childFragmentManager, null)
            }
        }

        binding.btnHeadphone.setOnClickListener {
            Intent(AudioEffect.ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL).apply {
                putExtra(AudioEffect.EXTRA_AUDIO_SESSION, viewModel.audioSessionId.value)
                putExtra(AudioEffect.EXTRA_PACKAGE_NAME, requireContext().packageName)
                putExtra(AudioEffect.EXTRA_CONTENT_TYPE, AudioEffect.EXTRA_CONTENT_TYPE)
            }.also {
                startActivityForResult(it, 133)
            }
        }

        binding.btnComment.setOnClickListener {
            val action =
                PlayerViewPagerFragmentDirections.actionPlayerViewPagerFragmentToCommentFragment(viewModel.song.value!!)
            findNavController().navigate(action)
        }


    }

    private fun setUpViewPager() {
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val fragmentList = arrayListOf<Fragment>(
            SongMainFragment(),
            LyricsFragment(),
        )

        val adapter = PlayerViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter = adapter
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
                binding.dot1.setImageResource(R.drawable.dot_selected)
                binding.dot2.setImageResource(R.drawable.dot_default)
            }

            1 -> {
                binding.dot2.setImageResource(R.drawable.dot_selected)
                binding.dot1.setImageResource(R.drawable.dot_default)
            }
        }
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