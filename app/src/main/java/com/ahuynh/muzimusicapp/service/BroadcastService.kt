package com.ahuynh.muzimusicapp.service

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log

class BroadcastService : Service() {

    private var countDownTimer: CountDownTimer? = null
    private val intents = Intent(COUNT_DOWN)
    private var milis: Long = 0L

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            milis = it.getLongExtra(DURATION, 30000L)
            Log.d(TAG, "Duration received: $milis")
            startCountdown(milis)
        }
        return START_NOT_STICKY
    }

    private fun startCountdown(millis: Long) {
        // Cancel any existing timer
        countDownTimer?.cancel()

        // Create a new timer
        countDownTimer = object : CountDownTimer(millis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                intents.putExtra(COUNT_DOWN, millisUntilFinished)
                sendBroadcast(intents)
            }

            override fun onFinish() {
                stopMusicService()

            }
        }
        countDownTimer?.start()
    }
    override fun onDestroy() {
        countDownTimer?.cancel()
        super.onDestroy()
    }

    private fun stopMusicService() {
        stopService(Intent(this,MusicService::class.java))
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        const val COUNT_DOWN = "count_down"
        const val DURATION = "duration"
        const val TAG = "BroadcastService"
    }
}