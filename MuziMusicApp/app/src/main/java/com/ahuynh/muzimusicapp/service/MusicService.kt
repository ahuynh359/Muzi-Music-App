package com.ahuynh.muzimusicapp.service

import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.os.IBinder
import android.util.Log
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.extractor.DefaultExtractorsFactory
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.model.Song
import com.ahuynh.muzimusicapp.utils.Constants.ACTION
import com.ahuynh.muzimusicapp.utils.Constants.DATA
import com.ahuynh.muzimusicapp.utils.Constants.NOTIFICATION_CHANNEL_NAME
import com.ahuynh.muzimusicapp.utils.Constants.NOTIFICATION_ID
import com.ahuynh.muzimusicapp.utils.Constants.SONG
import com.ahuynh.muzimusicapp.utils.Constants.SONG_LIST
import com.ahuynh.muzimusicapp.utils.EventBusModel
import com.ahuynh.muzimusicapp.utils.Helper.parcelable
import com.ahuynh.muzimusicapp.utils.Helper.parcelableArrayList
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

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

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val action = intent.getIntExtra(ACTION, 0)
        val data = intent.getBundleExtra(DATA)

        data?.let {
            val song: Song? = data.parcelable<Song>(SONG)
            val list: ArrayList<Song>? = data.parcelableArrayList<Song>(SONG_LIST)

            song?.let {

                songList = list!!
                currentSongIndex = songList.indexOf(song)
                Log.d("MusicService Current Song 1", currentSongIndex.toString())
                listenToMusic(currentSongIndex)

            }

        }
//
//        when (action) {
//            ACTION_PLAY -> {
//                playPauseMusic()
//            }
//        }

        return START_NOT_STICKY
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMusicTimeSeekEvent(event: EventBusModel.MusicTimeSeekEvent) {
        Log.d("MusicSerivce", "Seek to ${event.timeMillis}")
        player?.seekTo(event.timeMillis)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRequestSongEvent(event: EventBusModel.RequestSongEvent) {
        if (currentSongIndex > -1 && currentSongIndex < songList.size) {
            EventBus.getDefault()
                .postSticky(EventBusModel.SongInfoEvent(songList[currentSongIndex]))
            EventBus.getDefault().postSticky(EventBusModel.SongListEvent(songList))

            player?.let {
                EventBus.getDefault().postSticky(EventBusModel.MusicPlayingEvent(it.isPlaying))

            }
        }
    }

    private fun listenToMusic(currentSongIndex: Int) {

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
            // EventBus.getDefault().postSticky(EventBusModel.SongListEvent(songList))
        }
    }

    private fun addSongTail(song: Song) {
        val index = songList.indexOf(song)
        if (index == -1) {
            songList.add(song)
            //EventBus.getDefault().postSticky(EventBusModel.SongListEvent(songList))
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
                    sendTime(it)

                }
            player?.addListener(object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    EventBus.getDefault().postSticky(EventBusModel.MusicPlayingEvent(isPlaying))
                }


            })
        } catch (e: Exception) {
            Log.d("MusicService", e.toString())
            stopSelf()
        }
    }

    @kotlin.OptIn(DelicateCoroutinesApi::class)
    @OptIn(UnstableApi::class)
    private fun sendTime(player: ExoPlayer) {
        //Music is playing
        EventBus.getDefault().postSticky(EventBusModel.MusicPlayingEvent(true))

        jobTime?.cancel()

        jobTime = GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                player?.let {
                    Log.d("MusicService", player.currentPosition.toString())
                    Log.d("MusicService", player.duration.toString())
                    EventBus.getDefault().postSticky(
                        EventBusModel.MusicTimeEvent(
                            player.currentPosition,
                            player.duration
                        )
                    )
                    delay(1000) // Update every second
                }
            }
        }
        //EventBus.getDefault().postSticky(EventBusModel.AudioSessionIdEvent(player.audioSessionId))

    }


    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null

        val notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.cancel(NOTIFICATION_ID)

        jobTime?.cancel()

        EventBus.getDefault().postSticky(EventBusModel.SongInfoEvent(null))
        EventBus.getDefault().unregister(this)
    }


}

