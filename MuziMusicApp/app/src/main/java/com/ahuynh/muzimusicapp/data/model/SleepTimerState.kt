package com.ahuynh.muzimusicapp.data.model

data class SleepTimerState(
    val isTimerRunning: Boolean,
    val startButtonText: String,
    val seekBarProgress: Long
)