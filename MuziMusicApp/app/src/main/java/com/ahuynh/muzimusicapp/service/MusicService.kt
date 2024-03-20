package com.ahuynh.muzimusicapp.service

import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.extractor.DefaultExtractorsFactory
import com.ahuynh.muzimusicapp.model.Song
import com.ahuynh.muzimusicapp.utils.Constants.ACTION
import com.ahuynh.muzimusicapp.utils.Constants.ACTION_PLAY
import com.ahuynh.muzimusicapp.utils.Constants.DATA
import com.ahuynh.muzimusicapp.utils.Constants.SONG
import com.ahuynh.muzimusicapp.utils.Constants.SONG_LIST
import com.ahuynh.muzimusicapp.utils.EventBusModel
import com.ahuynh.muzimusicapp.utils.Helper.parcelable
import com.ahuynh.muzimusicapp.utils.Helper.parcelableArrayList
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
        Log.d("MusicService", "On Create")
        EventBus.getDefault().register(this)
//
//        val notification = NotificationCompat.Builder(
//            this@MusicService,
//            NOTIFICATION_CHANNEL_NAME,
//        )
//            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//            .setSmallIcon(R.drawable.song)
//            .setAutoCancel(false)
//            .build()
//        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("MusicService", "on start command")
        val action = intent.getIntExtra(ACTION, 0)
        val data = intent.getBundleExtra(DATA)

        data?.let {
            val song = data.parcelable<Song>(SONG)
            val list = data.parcelableArrayList<Song>(SONG_LIST)

            currentSong = song
            if (list != null) {
                songList = list
            }
            currentSongIndex = songList.indexOf(currentSong)
            changeMusic(currentSongIndex)
        }

        when (action) {
            ACTION_PLAY -> {
                playPauseMusic()
            }
        }

        return START_NOT_STICKY
    }

    private fun changeMusic(currentSongIndex: Int) {
        Log.d("MusicService", "change music")

        player?.let {
            if (it.isPlaying)
                it.stop()
            it.release()
        }
        val song = songList[currentSongIndex]
        currentSong = song

        EventBus.getDefault().postSticky(EventBusModel.SongInfoEvent(song))
        preparePlay(song)
    }

    private fun playPauseMusic() {
        player?.let {
            if (it.isPlaying) it.pause()
            else it.play()
            EventBus.getDefault().postSticky(EventBusModel.MusicPlayingEvent(it.isPlaying))
        }

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
                    sendInit(it)
                }
            player?.addListener(object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    EventBus.getDefault().postSticky(EventBusModel.MusicPlayingEvent(isPlaying))
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this, "Can't play this sing", Toast.LENGTH_SHORT).show()
            stopSelf()
        }
    }

    @OptIn(UnstableApi::class)
    private fun sendInit(player: ExoPlayer) {
        EventBus.getDefault().postSticky(EventBusModel.MusicPlayingEvent(true))
        timeSend()
        EventBus.getDefault().postSticky(EventBusModel.AudioSessionIdEvent(player.audioSessionId))

    }

    private fun timeSend() {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null

        jobTime?.cancel()
        EventBus.getDefault().postSticky(EventBusModel.SongInfoEvent(null))
        EventBus.getDefault().unregister(this)
    }


}

