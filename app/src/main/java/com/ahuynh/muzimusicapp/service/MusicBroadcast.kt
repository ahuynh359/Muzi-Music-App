package com.ahuynh.muzimusicapp.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ahuynh.muzimusicapp.utils.Constants.INTENT_ACTION

class MusicBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val actionMusic = intent?.getIntExtra(INTENT_ACTION, -1)
        val intentService = Intent(context, MusicService::class.java)
        intentService.putExtra("action", actionMusic)
        context?.startService(intentService)
    }

}
