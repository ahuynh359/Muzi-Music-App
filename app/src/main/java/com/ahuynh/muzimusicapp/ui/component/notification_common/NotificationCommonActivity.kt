package com.ahuynh.muzimusicapp.ui.component.notification_common

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.ActivityNotificationCommonBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.activity.BaseActivity
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.ui.component.user.UserActivity
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotificationCommonActivity :
    BaseActivity<ActivityNotificationCommonBinding>(ActivityNotificationCommonBinding::inflate) {

    private val viewModel by viewModels<NotificationCommonViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type = intent?.getStringExtra(Constants.TYPE)
        val referenceId = intent?.getLongExtra(Constants.REFERENCE_ID,-1)
        Log.d("ABCD",type.toString())
        Log.d("ABCD",referenceId.toString())

        if (type == null) {
            gotoHome()
        } else {
            when (type) {
                "COMMENT" -> {
                    if (referenceId != null) {
                        playSong(referenceId.toLong())
                    }
                }

                "SONG" -> {
                    if (referenceId != null) {
                        playSong(referenceId.toLong())
                    }
                }
                else -> gotoHome()


            }
        }

    }

    override fun getSnackbarView(): View {
        return binding.root
    }

    private fun gotoHome() {
        startActivity(Intent(this, UserActivity::class.java))
        finish()
    }

    private fun playSong(idSong: Long) {
        lifecycleScope.launch {
            val song = viewModel.getSong(idSong)
            if (song == null) {
                gotoHome()
            } else {
                Utils.sendMusic(
                    this@NotificationCommonActivity,
                    MusicService.ACTION_PLAY,
                    song,
                    ArrayList<Song>().apply { add(song) }
                )
                startActivity(
                    Intent(
                        this@NotificationCommonActivity,
                        PlayerActivity::class.java
                    )
                )
                finish()
            }
        }
    }


}