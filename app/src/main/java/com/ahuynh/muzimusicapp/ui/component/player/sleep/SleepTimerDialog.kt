package com.ahuynh.muzimusicapp.ui.component.player.sleep

import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.databinding.DialogSleepTimerBinding
import com.ahuynh.muzimusicapp.ui.component.player.PlayerViewModel
import java.util.Locale

class SleepTimerDialog : DialogFragment() {
    private var isTimerRunning = false
    private lateinit var binding: DialogSleepTimerBinding
    private var seekBarProgress: Long = 0
    private val viewModel by viewModels<PlayerViewModel>({ requireActivity() })
    private var timer: CountDownTimer? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogSleepTimerBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        handleUI()
        super.onViewCreated(view, savedInstanceState)
    }


    private fun handleUI() {
        binding.tvMin.text = "$seekBarProgress sec"
        binding.btnStart.setOnClickListener {

            Toast.makeText(context, "$seekBarProgress is set", Toast.LENGTH_SHORT).show()
            if (isTimerRunning) {
                stopTimer()
            }
            startTimer()
            this.dismiss()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                seekBarProgress = progress.toLong()
                binding.tvMin.text = "$seekBarProgress sec"

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })


    }




    private fun startTimer() {
        if (!isTimerRunning) {
            val time = seekBarProgress * 1000 ;

            timer = object : CountDownTimer(time, 1000) { // 10 seconds countdown
                override fun onTick(millisUntilFinished: Long) {
                    val totalSeconds = millisUntilFinished / 1000
                    val hours = totalSeconds / 3600
                    val min = (totalSeconds % 3600) / 60
                    val sec = totalSeconds % 60
                    val time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, min, sec)
                    viewModel.sleepTime.postValue(time)
                    isTimerRunning = true
                }

                override fun onFinish() {
                    isTimerRunning = false
                }
            }.start()


        }
    }

    private fun stopTimer() {
        timer?.cancel()
        isTimerRunning = false
    }


}