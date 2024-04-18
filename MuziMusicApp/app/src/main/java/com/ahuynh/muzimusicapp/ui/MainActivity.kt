package com.ahuynh.muzimusicapp.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.ActivityMainBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Constants.PERMISSION_REQUEST_ID
import com.ahuynh.muzimusicapp.utils.EventBusModel
import com.ahuynh.muzimusicapp.utils.NetworkConnectivityHelper
import com.ahuynh.muzimusicapp.utils.Utils.appSettingOpen
import com.ahuynh.muzimusicapp.utils.Utils.checkSinglePermissionAny
import com.ahuynh.muzimusicapp.utils.Utils.showWarningDialog
import com.ahuynh.muzimusicapp.utils.VersionHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding :  ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var snackbar: Snackbar
    private val networkConnectivityObserver: NetworkConnectivityHelper by lazy {
        NetworkConnectivityHelper(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestPermission()
        setUpNavigationGraph()

        getData()
        observe()
        handleUI()



    }

    private fun handleUI() {
        binding.player.setOnClickListener {
            startActivity(Intent(this,PlayerActivity::class.java))
        }
        binding.btnPlayPause.setOnClickListener {
            sendMusic(MusicService.ACTION_PLAY)
        }
        binding.btnNext.setOnClickListener {
            sendMusic(MusicService.ACTION_NEXT)
        }
        binding.btnPre.setOnClickListener {
            sendMusic(MusicService.ACTION_PRE)
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

    private fun getData() {
        viewModel.restoreState()
        viewModel.getAllSongs()

        viewModel.songList.observe(this) {
            Constants.SONG_LIST_DATA = it
        }
    }

    private fun observe() {
        viewModel.song.observe(this) {
            if (it == null) {
                binding.player.visibility = View.GONE
            } else {
                binding.player.visibility = View.VISIBLE
                binding.tvSong.text = it.name
                binding.tvSinger.text = it.singer
                Glide
                    .with(binding.imvSong.context)
                    .load(it.image)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.drawable.note)
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





    private fun requestPermission() {
        if (checkSinglePermissionAny(
                this, Manifest.permission.POST_NOTIFICATIONS,
                PERMISSION_REQUEST_ID
            )
        ) {
            Toast.makeText(this@MainActivity, "Permission Granted", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (!(requestCode == PERMISSION_REQUEST_ID && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            ) {
                appSettingOpen(this)
            } else {
                showWarningDialog(this)
            }
        }

    }


    private fun setUpNavigationGraph() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
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
    }

}