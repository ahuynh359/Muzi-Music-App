package com.ahuynh.muzimusicapp.service

import android.app.PendingIntent
import android.app.Service
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
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
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.ahuynh.muzimusicapp.MuziMusicApplication
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Constants.ACTION
import com.ahuynh.muzimusicapp.utils.Constants.DATA
import com.ahuynh.muzimusicapp.utils.Constants.INTENT_ACTION
import com.ahuynh.muzimusicapp.utils.Constants.NOTIFICATION_ID
import com.ahuynh.muzimusicapp.utils.Constants.SONG
import com.ahuynh.muzimusicapp.utils.Constants.SONG_LIST
import com.ahuynh.muzimusicapp.utils.EventBusModel
import com.ahuynh.muzimusicapp.utils.Utils.parcelable
import com.ahuynh.muzimusicapp.utils.Utils.parcelableArrayList
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
    companion object {
        const val ACTION_PLAY = 12
        const val ACTION_PRE = 13
        const val ACTION_NEXT = 14
        const val ACTION_CLEAR = 15
        const val ACTION_DO_SOMETHING = 16
        const val ACTION_ADD_SONG_NEXT = 17
        const val ACTION_ADD_SONG_TAIL = 18
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        EventBus.getDefault().register(this)
        createNotification()

    }

    private fun createNotification() {
        val notification = NotificationCompat.Builder(
            this@MusicService,
            MuziMusicApplication.NOTIFICATION_CHANNEL_ID,
        )
            .setSmallIcon(R.drawable.note)
            .setAutoCancel(false)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    //Get music and list sent from PlayerActivity, handle event
    @OptIn(UnstableApi::class)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val action = intent.getIntExtra(ACTION, 0)
        val data = intent.getBundleExtra(DATA)



        data?.let {
            val song: Song? = data.parcelable<Song>(SONG)
            val list: ArrayList<Song>? = data.parcelableArrayList<Song>(SONG_LIST)

            song?.let {
                list?.let {
                    songList = list
                    Log.d("ABC Shuffle",Constants.IS_SHUFFLE.toString())
                    if (Constants.IS_SHUFFLE) {
                        val shuffledSongList = ArrayList(songList).apply { shuffle() }
                        songList = shuffledSongList
                    }
                    currentSongIndex = songList.indexOf(song)
                    listenToMusic(currentSongIndex)

                }
            }
        }
        handleAction(action)

        return START_NOT_STICKY
    }

    private fun handleAction(action: Int) {

        when (action) {
            ACTION_PLAY -> {
                playPauseMusic()
            }

            ACTION_PRE -> {
                prev()
            }

            ACTION_NEXT -> {

                next()
            }

            ACTION_CLEAR -> {
                EventBus.getDefault().postSticky(EventBusModel.ClearMusic())
                stopSelf()
            }
        }

    }

    private fun prev() {
        if (currentSongIndex > 0) currentSongIndex--
        if (Constants.IS_REPEAT) {
            if (currentSongIndex > 0) currentSongIndex-- else
                currentSongIndex = songList.size - 1
        }
        listenToMusic(currentSongIndex)

    }

    private fun next() {
        if (currentSongIndex + 1 < songList.size) {
            currentSongIndex++;
            listenToMusic(currentSongIndex)
        } else {
            if (Constants.IS_REPEAT) {
                currentSongIndex = 0
                listenToMusic(currentSongIndex)
            } else {
                //Last song on list
                player?.playWhenReady = false
                player?.stop();
                player?.seekTo(0)
                EventBus.getDefault().postSticky(EventBusModel.MusicPlayingEvent(false))
                sendNotification()
            }
        }
    }

    private fun listenToMusic(currentSongIndex: Int) {

        //If current music is playing then stop
        player?.let {
            if (it.isPlaying)
                it.stop()

            it.release()
        }

        val song = songList[currentSongIndex]
        currentSong = song

        //Send current song info back to UI
        EventBus.getDefault().postSticky(EventBusModel.SongInfoEvent(song))
        preparePlay(song)
        sendNotification()
    }

    //Update button play pause
    private fun playPauseMusic() {
        player?.let {
            if (it.isPlaying) it.pause()
            else it.play()
            EventBus.getDefault().postSticky(EventBusModel.MusicPlayingEvent(it.isPlaying))
        }
        sendNotification()

    }



    //Use coil to load image from url convert to bitmap with coroutine
    private suspend fun getCurrentSongBitMap(): Bitmap {
        val loader = ImageLoader(this@MusicService)
        val request =
            ImageRequest.Builder(this@MusicService)
                .data(songList[currentSongIndex].image)
                .allowHardware(false)
                .build()

        var bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.note)
        try {
            val result = (loader.execute(request) as SuccessResult).drawable
            bitmap = (result as BitmapDrawable).bitmap
        } catch (_: Exception) {
        }
        return bitmap
    }

    @kotlin.OptIn(DelicateCoroutinesApi::class)
    private fun sendNotification() {
        GlobalScope.launch(Dispatchers.Main) {

            val bitmap = getCurrentSongBitMap()

        player?.let { media ->
            val song = songList[currentSongIndex]

            //Handle when click on notification
            val resultIntent = Intent(this@MusicService, PlayerActivity::class.java)
            val resultPendingIntent: PendingIntent? =
                TaskStackBuilder.create(this@MusicService).run {
                    addNextIntentWithParentStack(resultIntent)
                    getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                }
            val notification =
                NotificationCompat.Builder(this@MusicService, MuziMusicApplication.NOTIFICATION_CHANNEL_ID)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setSmallIcon(R.drawable.note)
                    .setContentIntent(resultPendingIntent)
                    .addAction(
                        R.drawable.ic_pre_small,
                        "Pre",
                        getPendingIntent(this@MusicService, ACTION_PRE)
                    )
                    .addAction(
                        if (media.isPlaying) R.drawable.ic_pause_small else R.drawable.ic_play_small,
                        "Play",
                        getPendingIntent(this@MusicService, ACTION_PLAY)
                    )
                    .addAction(
                        R.drawable.ic_next_small,
                        "Next",
                        getPendingIntent(this@MusicService, ACTION_NEXT)
                    )
                    .setProgress(
                        media.duration.toInt(),
                        media.currentPosition.toInt(),
                        false
                    )
                    .setStyle(
                        androidx.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(0, 1, 2)
                    )


                    .setContentTitle(song.name)
                    .setContentText(song.singer)
                    .setLargeIcon(bitmap)
                    .setAutoCancel(false)
                    .setOngoing(true)
                    .build()
            startForeground(NOTIFICATION_ID, notification)
        }
        }

    }

    private fun getPendingIntent(context: Context, action: Int): PendingIntent? {
        val intent = Intent(this, MusicBroadcast::class.java)
        intent.putExtra(INTENT_ACTION, action)
        return PendingIntent.getBroadcast(
            context.applicationContext,
            action,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


    }

    @OptIn(UnstableApi::class)
    private fun preparePlay(song: Song) {
        try {
            player = ExoPlayer.Builder(this)
                .setMediaSourceFactory(DefaultMediaSourceFactory(this@MusicService))
                .build().also {
                    val mediaItem = MediaItem.fromUri(song.file!!)
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

                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)
                    when(playbackState) {
                        Player.STATE_ENDED ->{
                            next()
                            Log.d("ABCDE","ENDed")
                        }

                        Player.STATE_BUFFERING -> {
                        }

                        Player.STATE_IDLE -> {
                        }

                        Player.STATE_READY -> {
                        }
                    }
                }


            })
        } catch (e: Exception) {
            stopSelf()
        }
    }

    @kotlin.OptIn(DelicateCoroutinesApi::class)
    @OptIn(UnstableApi::class)
    private fun sendTime(player: ExoPlayer) {
        EventBus.getDefault().postSticky(EventBusModel.MusicPlayingEvent(true))

        jobTime?.cancel()

        jobTime = GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                player.let {
                    EventBus.getDefault().postSticky(
                        EventBusModel.MusicTimeEvent(
                            player.currentPosition,
                            player.duration
                        )
                    )
                    delay(1000)
                }
            }
        }

        jobTime?.start()
        sendNotification()
    }

    // Seekbar seek time
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMusicTimeSeekEvent(event: EventBusModel.MusicTimeSeekEvent) {
        player?.seekTo(event.timeMillis)
    }

    //Return Song info
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

    override fun onDestroy() {
        super.onDestroy()
        player?.playWhenReady = false
        player?.stop();

        player?.release()
        player = null

        val notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.cancel(NOTIFICATION_ID)

        jobTime?.cancel()

        EventBus.getDefault().postSticky(EventBusModel.SongInfoEvent(null))
        EventBus.getDefault().unregister(this)
    }


}

