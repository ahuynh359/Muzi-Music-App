package com.ahuynh.muzimusicapp.ui.upload

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.aceinteract.android.stepper.StepperNavListener
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.databinding.ActivityUploadBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UploadActivity : AppCompatActivity(), StepperNavListener {
    private lateinit var binding: ActivityUploadBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<UploadVIewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpNavigationGraph()
        setupStepper()

    }

    override fun onCompleted() {
        TODO("Not yet implemented")
    }

    override fun onStepChanged(step: Int) {
        if (step == 4) {
            binding.fabNext.visibility = View.GONE
        } else
            binding.fabNext.visibility = View.VISIBLE
        if (step == 0) {
            binding.fabPrevious.visibility = View.GONE
        } else
            binding.fabPrevious.visibility = View.VISIBLE
    }


    private fun setUpNavigationGraph() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.frame_stepper) as NavHostFragment
        navController = navHostFragment.navController

    }

    private fun setupStepper() {
        binding.stepper.setupWithNavController(findNavController(R.id.frame_stepper))
        binding.stepper.stepperNavListener = this
        binding.fabNext.setOnClickListener {
            if (binding.stepper.currentStep == 4) {
            } else
                binding.stepper.goToNextStep()
        }
        binding.fabPrevious.setOnClickListener {
            binding.stepper.goToPreviousStep()
        }
    }
}