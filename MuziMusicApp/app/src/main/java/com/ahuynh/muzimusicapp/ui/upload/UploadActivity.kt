package com.ahuynh.muzimusicapp.ui.upload

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aceinteract.android.stepper.StepperNavListener
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.databinding.ActivityUploadBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UploadActivity : AppCompatActivity(), StepperNavListener {
    private lateinit var binding: ActivityUploadBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<UploadViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpNavigationGraph()
        setupStepper()
        handleUI()

    }

    private fun handleUI() {

    }

    override fun onCompleted() {
        TODO("Not yet implemented")
    }

    override fun onStepChanged(step: Int) {
        if (step == 4) {
            binding.fabDone.visibility = View.VISIBLE
            binding.fabNext.visibility = View.GONE
        } else {
            binding.fabNext.visibility = View.VISIBLE
            binding.fabDone.visibility = View.GONE
        }
        if (step == 0) {
            binding.fabPrevious.visibility = View.GONE
        } else
            binding.fabPrevious.visibility = View.VISIBLE
    }


    private fun setUpNavigationGraph() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.frame_stepper) as NavHostFragment
        navController = navHostFragment.navController
        binding.stepper.setupWithNavController(navController)

    }

    private fun setupStepper() {

        binding.stepper.stepperNavListener = this
        binding.fabNext.setOnClickListener {
            binding.stepper.goToNextStep()
        }
        binding.fabPrevious.setOnClickListener {
            binding.stepper.goToPreviousStep()
        }
        binding.fabDone.setOnClickListener {
            addOrShowError()
        }
    }

    private fun checkError() : Int{
        if(viewModel.songName.trim().isEmpty()){
            Toast.makeText(this,"Song name is empty",Toast.LENGTH_SHORT).show()
            return 0
        } else if(viewModel.songFile == null){
            Toast.makeText(this,"Song file is empty",Toast.LENGTH_SHORT).show()
            return 1
        } else if(viewModel.singerName.trim().isEmpty()){
            Toast.makeText(this,"Singer name is empty",Toast.LENGTH_SHORT).show()
            return 2
        } else if(viewModel.imageFile == null){
            Toast.makeText(this,"Image song is empty",Toast.LENGTH_SHORT).show()
            return 3
        } else if(viewModel.lyrics.trim().isEmpty()){
            Toast.makeText(this,"Lyrics is empty",Toast.LENGTH_SHORT).show()
            return 4
        }
        return 5
    }

    private fun addOrShowError() {
       val errorIndex = checkError()
        if(errorIndex != 5){
            while(binding.stepper.currentStep != errorIndex) binding.stepper.goToPreviousStep()
        } else
            viewModel.addSong()

    }


}