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
import com.ahuynh.muzimusicapp.ui.component.user.UserActivity
import com.ahuynh.muzimusicapp.ui.component.user.comment.CommentActivity
import com.ahuynh.muzimusicapp.ui.component.user.song.menu.SongMenu
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.EventBusModel
import com.ahuynh.muzimusicapp.utils.Utils.toTimeFormat
import com.ahuynh.muzimusicapp.utils.helper.VersionHelper
import com.google.android.material.slider.Slider
import org.greenrobot.eventbus.EventBus

class PlayerViewPagerFragment :
    BaseFragment<FragmentPlayerViewPagerBinding>(FragmentPlayerViewPagerBinding::inflate) {

    private val viewModel by viewModels<PlayerViewModel>({ requireActivity() })
    private var isSliderPressed: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        setupViewPager()
        setupSeekbar()
        setupButtons()
    }

    private fun setupViewPager() {
        val fragmentList = arrayListOf<Fragment>(
            SongMainFragment(),
            LyricsFragment()
        )
        val adapter = PlayerViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.viewPager.apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            this.adapter = adapter
            currentItem = 0
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    dotIndicator(position)
                }
            })
        }
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

    private fun setupSeekbar() {
        binding.slider.apply {
            setLabelFormatter { value: Float -> (value / 1000).toInt().toTimeFormat() }
            addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: Slider) {
                    isSliderPressed = true
                }

                override fun onStopTrackingTouch(slider: Slider) {
                    EventBus.getDefault()
                        .post(EventBusModel.MusicTimeSeekEvent(slider.value.toLong()))
                    isSliderPressed = false
                    viewModel.isUserTouchSlider = true
                    viewModel.currentSongTime.postValue(slider.value.toInt())
                }
            })
        }
    }

    private fun setupButtons() {
        binding.apply {
            btnShuffle.setOnClickListener { toggleShuffle() }
            btnRepeat.setOnClickListener { toggleRepeat() }
            btnPlayPause.setOnClickListener { togglePlayPause() }
            btnPre.setOnClickListener { playPrevious() }
            btnNext.setOnClickListener { playNext() }
            btnDown.setOnClickListener { navigateToUserActivity() }
            btnSleep.setOnClickListener { navigateToSleepDialog() }
            btnHeadphone.setOnClickListener { openAudioEffectControlPanel() }
            btnComment.setOnClickListener { navigateToCommentActivity() }
            tvSinger.setOnClickListener { navigateToSingerMenu() }
            tvType.setOnClickListener { navigateToTypeMenu() }
            btnHeart.setOnClickListener { toggleLoveSong() }
//            btnMore.setOnClickListener { navigateToSongMenu() }
        }
    }

    private fun navigateToSongMenu() {
        val fragment = SongMenu()
        fragment.arguments = Bundle().apply {
            putParcelable(Constants.SONG, viewModel.song.value)
        }
        fragment.show(requireActivity().supportFragmentManager, null)
    }

    private fun toggleShuffle() {
        val value = viewModel.isShuffle.value ?: false
        viewModel.setShuffle(!value)
    }

    private fun toggleRepeat() {
        val value = viewModel.isRepeat.value ?: false
        viewModel.setRepeat(!value)
    }

    private fun togglePlayPause() {
        if (viewModel.isClear) {
            sendMusic(MusicService.ACTION_PLAY, viewModel.song.value, viewModel.songList.value!!)
            viewModel.isClear = false
        } else {
            sendMusic(MusicService.ACTION_PLAY)
        }
    }

    private fun playPrevious() {
        binding.viewPager.currentItem = 0
        viewModel.currentSongTime.postValue(0)
        binding.slider.value = 0f
        if (viewModel.isClear) {
            sendMusic(MusicService.ACTION_PRE, viewModel.song.value, viewModel.songList.value!!)
            viewModel.isClear = false
        } else {
            sendMusic(MusicService.ACTION_PRE)
        }
    }

    private fun playNext() {
        binding.viewPager.currentItem = 0
        viewModel.currentSongTime.postValue(0)
        binding.slider.value = 0f
        if (viewModel.isClear) {
            sendMusic(MusicService.ACTION_NEXT, viewModel.song.value, viewModel.songList.value!!)
            viewModel.isClear = false
        } else {
            sendMusic(MusicService.ACTION_NEXT)
        }
    }

    private fun navigateToUserActivity() {
//        val options = ActivityOptions.makeCustomAnimation(
//            requireContext(),
//            R.anim.slide_in_top,
//            R.anim.slide_out_bottom
//        ).toBundle()
        requireActivity().finish()
        requireActivity().startActivity(Intent(requireContext(), UserActivity::class.java))
    }

    private fun navigateToSleepDialog() {
        val action = PlayerViewPagerFragmentDirections.actionPlayerViewPagerFragmentToSleepDialog()
        findNavController().navigate(action)
    }

    private fun openAudioEffectControlPanel() {
        Intent(AudioEffect.ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL).apply {
            putExtra(AudioEffect.EXTRA_AUDIO_SESSION, viewModel.audioSessionId.value)
            putExtra(AudioEffect.EXTRA_PACKAGE_NAME, requireContext().packageName)
            putExtra(AudioEffect.EXTRA_CONTENT_TYPE, AudioEffect.EXTRA_CONTENT_TYPE)
        }.also {
            startActivityForResult(it, 133)
        }
    }

    private fun navigateToCommentActivity() {
        startActivity(Intent(requireActivity(), CommentActivity::class.java).apply {
            putExtra(Constants.SONG, viewModel.song.value)
        })
    }

    private fun navigateToSingerMenu() {
        val action =
            PlayerViewPagerFragmentDirections.actionPlayerViewPagerFragmentToSingerMenu(viewModel.song.value!!)
        findNavController().navigate(action)
    }

    private fun navigateToTypeMenu() {
        val action =
            PlayerViewPagerFragmentDirections.actionPlayerViewPagerFragmentToTypeMenu(viewModel.song.value!!)
        findNavController().navigate(action)
    }

    private fun toggleLoveSong() {
        viewModel.loveOrUnlove(viewModel.song.value?.id!!)
    }

    private fun observeViewModel() {
        viewModel.apply {
            getShuffle()
            getRepeat()
            audioSessionId.observe(viewLifecycleOwner) { sessionId ->
                if (sessionId != 0) {
                    binding.visualizer.setColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                    binding.visualizer.setPlayer(sessionId)
                }
            }
            isShuffle.observe(viewLifecycleOwner) { isShuffle ->
                binding.btnShuffle.setImageResource(
                    if (isShuffle) R.drawable.ic_shuffle_selected else R.drawable.ic_shuffle
                )
            }
            isRepeat.observe(viewLifecycleOwner) { isRepeat ->
                binding.btnRepeat.setImageResource(
                    if (isRepeat) R.drawable.ic_repeat_selected else R.drawable.ic_repeat
                )
            }
            isPlaying.observe(viewLifecycleOwner) { isPlaying ->
                binding.btnPlayPause.setImageResource(
                    if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
                )
            }
            song.observe(viewLifecycleOwner) { song ->
                isUserLoveSong(song.id)
                binding.tvSinger.text = song.singers.joinToString(", ") { it.name }
                binding.tvType.text = song.types.joinToString(", ") { it.name }
            }
            loveSong.observe(viewLifecycleOwner) { isLoved ->
                binding.btnHeart.setImageResource(
                    if (isLoved) R.drawable.ic_hearted else R.drawable.ic_heart_small
                )
            }
            duration.observe(viewLifecycleOwner) { duration ->
                handleDuration(duration)
            }
        }
    }

    private fun handleDuration(duration: Long) {
        viewModel.timeMillis.observe(viewLifecycleOwner) { time ->
            if (duration > 0) {
                binding.shimmerSlider.stopShimmer()
                binding.slider.visibility = View.VISIBLE
                binding.shimmerSlider.visibility = View.GONE
                binding.tvEndTime.text = (duration / 1000).toInt().toTimeFormat()
                if (!isSliderPressed) binding.slider.value = time.toFloat()
                binding.slider.valueTo = duration.toFloat()
                binding.tvStartTime.text = (time / 1000).toInt().toTimeFormat()
                viewModel.currentSongTime.postValue(time.toInt())
            } else {
                binding.shimmerSlider.visibility = View.VISIBLE
                binding.slider.visibility = View.INVISIBLE
                binding.shimmerSlider.startShimmer()
                binding.tvEndTime.text = "N/A"
                binding.tvStartTime.text = "00:00"
            }
        }
    }

    private fun sendMusic(
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
}