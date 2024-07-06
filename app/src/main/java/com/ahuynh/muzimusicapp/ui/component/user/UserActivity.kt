package com.ahuynh.muzimusicapp.ui.component.user

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.databinding.ActivityUserBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.activity.BaseActivity
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Constants.PERMISSION_REQUEST_ID
import com.ahuynh.muzimusicapp.utils.EventBusModel
import com.ahuynh.muzimusicapp.utils.Utils
import com.ahuynh.muzimusicapp.utils.helper.PermissionHelper.appSettingOpen
import com.ahuynh.muzimusicapp.utils.helper.PermissionHelper.checkMultiplePermission
import com.ahuynh.muzimusicapp.utils.helper.PermissionHelper.warningPermissionDialog
import com.ahuynh.muzimusicapp.utils.helper.ToastHelper.makeToastPermissionGranted
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


@AndroidEntryPoint
class UserActivity : BaseActivity<ActivityUserBinding>(ActivityUserBinding::inflate) {

    companion object {
        const val TAG = "UserActivity"
    }

    private lateinit var navController: NavController
    private val viewModel by viewModels<UserViewModel>()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermission()
        setUpNavigationGraph()

        getData()
        observe()
        handleUI()
    }
    private fun getData() {
        viewModel.restoreState()
    }

    private fun handleUI() {
        binding.player.setOnClickListener {
            startActivity(Intent(this,PlayerActivity::class.java))
        }
        binding.btnPlayPause.setOnClickListener {
            Utils.sendMusic(applicationContext, MusicService.ACTION_PLAY)
        }
        binding.btnNext.setOnClickListener {
            Utils.sendMusic(applicationContext, MusicService.ACTION_NEXT)
        }
        binding.btnPre.setOnClickListener {
            Utils.sendMusic(applicationContext, MusicService.ACTION_PRE)
        }
//        binding.toolbar.btnSearch.setOnClickListener {
//            startActivity(Intent(this, SearchActivity::class.java))
//        }
    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)

    }

    override fun getSnackbarView(): View {
        return binding.main
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }


    private fun observe() {
        viewModel.song.observe(this) {
            if (it == null) {
                binding.player.visibility = View.GONE
            } else {
                binding.player.visibility = View.VISIBLE
                binding.tvSong.text = it.name
                binding.tvSinger.text =it.singers.joinToString(", ") { it.name }
                Glide
                    .with(binding.imvSong.context)
                    .load(it.avatar)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.imvSong)
            }
        }

        viewModel.isPlaying.observe(this) {
            binding.btnPlayPause.setImageResource(
                if (it) R.drawable.ic_pause_small
                else R.drawable.ic_play_small
            )
        }


    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermission() {
        if (checkMultiplePermission(this, PERMISSION_REQUEST_ID)) {
            makeToastPermissionGranted(this)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_ID) {
            if (grantResults.isNotEmpty()) {
                var isGrant = true
                for (element in grantResults) {
                    if (element == PackageManager.PERMISSION_DENIED) {
                        isGrant = false
                    }
                }
                if (isGrant) {
                    makeToastPermissionGranted(this)
                } else {
                    var someDenied = false
                    for (permission in permissions) {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                permission
                            )
                        ) {
                            if (ActivityCompat.checkSelfPermission(
                                    this,
                                    permission
                                ) == PackageManager.PERMISSION_DENIED
                            ) {
                                someDenied = true
                            }
                        }
                    }
                    if (someDenied) {
                        appSettingOpen(this)
                    } else {
                        warningPermissionDialog(this) { _: DialogInterface, which: Int ->
                            when (which) {
                                DialogInterface.BUTTON_POSITIVE ->
                                    checkMultiplePermission(this, PERMISSION_REQUEST_ID)
                            }
                        }
                    }
                }
            }

        }
    }


    private fun setUpNavigationGraph() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.btmNavigation,navController)
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
            binding.slider.visibility = View.VISIBLE
            binding.slider.value = event.timeMillis.toFloat()
            binding.slider.valueTo = event.duration.toFloat()
        } else{
            binding.slider.valueTo = 0f
            binding.slider.visibility = View.GONE
            binding.slider.value = 0f
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onClearMusic(event: EventBusModel.ClearMusic) {
        viewModel.isPlaying.postValue(false)
        binding.slider.value = 0f
        binding.slider.valueTo = 0f
    }

}