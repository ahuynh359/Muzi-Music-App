package com.ahuynh.muzimusicapp.service

import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.extractor.DefaultExtractorsFactory
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.model.Song
import com.ahuynh.muzimusicapp.utils.Constants.NOTIFICATION_CHANNEL_NAME
import com.ahuynh.muzimusicapp.utils.Constants.NOTIFICATION_ID
import com.ahuynh.muzimusicapp.utils.EventBusModel
import kotlinx.coroutines.Job
import org.greenrobot.eventbus.EventBus

class MusicService : Service() {

    private var player: ExoPlayer? = null
    private var jobTime: Job? = null
    private var songList: ArrayList<Song> = arrayListOf()
    private var currentSong: Song? = null
    private var currentSongIndex: Int = -1
    private lateinit var defaultBitmap: Bitmap

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        EventBus.getDefault().register(this)

        val notification = NotificationCompat.Builder(
            this@MusicService,
            NOTIFICATION_CHANNEL_NAME,
        )
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.song)
            .setAutoCancel(false)
            .build()
        startForeground(NOTIFICATION_ID, notification)
    }


    private fun addSongNext(song: Song) {
        val index = songList.indexOf(song)
        if (index == -1) {
            songList.add(currentSongIndex + 1, song)
            EventBus.getDefault().postSticky(EventBusModel.SongListEvent(songList))
        }
    }

    private fun addSongTail(song: Song) {
        val index = songList.indexOf(song)
        if (index == -1) {
            songList.add(song)
            EventBus.getDefault().postSticky(EventBusModel.SongListEvent(songList))
        }
    }

    @OptIn(UnstableApi::class)
    private fun preparePlay(song: Song) {
        try {
            player = ExoPlayer.Builder(this)
                .setMediaSourceFactory(DefaultMediaSourceFactory(this@MusicService))
                .build().also {
                    var mediaItem = MediaItem.fromUri(song.file!!)
                    val dataSourceFactory = DefaultDataSource.Factory(this)
                    val extractorsFactory =
                        DefaultExtractorsFactory().setConstantBitrateSeekingEnabled(true)
                    val progressiveMediaSource =
                        ProgressiveMediaSource.Factory(dataSourceFactory, extractorsFactory)
                            .createMediaSource(mediaItem)
                    it.setMediaSource(progressiveMediaSource)
                    it.prepare()
                    it.play()
                    //sendInit(it)
                }
        } catch (e: Exception) {
            Toast.makeText(this, "Can't play this sing", Toast.LENGTH_SHORT).show()
            stopSelf()
        }
    }


}