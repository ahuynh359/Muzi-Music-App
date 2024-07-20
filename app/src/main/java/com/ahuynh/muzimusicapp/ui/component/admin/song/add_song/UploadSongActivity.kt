package com.ahuynh.muzimusicapp.ui.component.admin.song.add_song

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aceinteract.android.stepper.StepperNavListener
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.databinding.ActivityUploadSongBinding
import com.ahuynh.muzimusicapp.databinding.ActivityUserBinding
import com.ahuynh.muzimusicapp.ui.base.activity.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UploadSongActivity :
    BaseActivity<ActivityUploadSongBinding>(ActivityUploadSongBinding::inflate),
    StepperNavListener {

    private lateinit var navController: NavController
    private val viewModel by viewModels<UploadViewModel>()

    companion object {
        const val TAG = "UploadSongActivity"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpNavigationGraph()
        setupStepper()
        observe()


    }

    override fun getSnackbarView(): View {
        return binding.main
    }


    private fun observe() {
        viewModel.isLoading.observe(this) {
            if (it) {
//                binding.fabPrevious.visibility = View.GONE
//                binding.fabDone.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                binding.progressBar.show()
            } else {
                binding.progressBar.visibility = View.GONE
                binding.progressBar.hide()

            }
        }


        viewModel.addSongStatus.observe(this) {
            if (it != null) {
                Toast.makeText(this, "Upload Song Successfully ", Toast.LENGTH_SHORT).show()
                onBackPressedDispatcher.onBackPressed()

            }
            viewModel.addSongStatus.postValue(null);
        }

    }


    override fun onCompleted() {
    }

    override fun onStepChanged(step: Int) {
        Toast.makeText(this, "Step $step", Toast.LENGTH_SHORT).show()
        if (step ==2) {
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

    private fun checkError(): Int {
        if (viewModel.name.trim().isEmpty()) {
            Toast.makeText(this, "Song name is empty", Toast.LENGTH_SHORT).show()
            return 0
        } else if (viewModel.avatar == null) {
            Toast.makeText(this, "Avatar is empty", Toast.LENGTH_SHORT).show()
            return 1
        } else if (viewModel.file == null) {
            Toast.makeText(this, "File is empty", Toast.LENGTH_SHORT).show()
            return 2
        } else if (viewModel.lyrics.isEmpty()) {
            Toast.makeText(this, "Lyrics is empty", Toast.LENGTH_SHORT).show()
            return 3
        } else if (viewModel.albumId == null) {
            Toast.makeText(this, "Album Id is null", Toast.LENGTH_SHORT).show()
            return 5
        } else if (viewModel.singerIds.isEmpty()) {
            Toast.makeText(this, "Singer Id is null", Toast.LENGTH_SHORT).show()
            return 6
        } else if (viewModel.typeIds.isEmpty()) {
            Toast.makeText(this, "Type Id is null", Toast.LENGTH_SHORT).show()
            return 7
        }
        return 8
    }

    private fun addOrShowError() {
        val errorIndex = checkError()
        if (errorIndex != 8) {
            while (binding.stepper.currentStep != errorIndex) binding.stepper.goToPreviousStep()
        } else {
            viewModel.addSong()

        }

    }


}