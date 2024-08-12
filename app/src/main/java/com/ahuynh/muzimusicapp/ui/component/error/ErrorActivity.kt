package com.ahuynh.muzimusicapp.ui.component.error

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ahuynh.muzimusicapp.R


class ErrorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error)
        val restartButton = findViewById<Button>(R.id.btn_restart)
        restartButton.setOnClickListener {
            restartApp()
        }
    }

    private fun restartApp() {
        val intent = baseContext.packageManager
            .getLaunchIntentForPackage(baseContext.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}