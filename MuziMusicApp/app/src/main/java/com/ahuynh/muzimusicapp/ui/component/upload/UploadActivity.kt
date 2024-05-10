package com.ahuynh.muzimusicapp.ui.component.upload

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aceinteract.android.stepper.StepperNavListener
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.SongPost
import com.ahuynh.muzimusicapp.databinding.ActivityUploadBinding
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Constants.TOKEN_13
import com.ahuynh.muzimusicapp.utils.Constants.TOKEN_14
import dagger.hilt.android.AndroidEntryPoint
import fcm.androidtoandroid.FirebasePush
import fcm.androidtoandroid.model.Notification
import org.json.JSONArray

@AndroidEntryPoint
class UploadActivity : AppCompatActivity(), StepperNavListener {
    private lateinit var binding: ActivityUploadBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<UploadViewModel>()
    companion object{
        const val TAG = "UploadActivity"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpNavigationGraph()
        setupStepper()
        observe()




    }

    private fun send(nameSong: String, singer: String) {
        val notification =
            Notification("Notification New Song", "Name : $nameSong Singer : $singer")


        val firebasePush = FirebasePush.build(Constants.SERVER_KEY)
            .setNotification(notification)

        val jsonArray = JSONArray();
        jsonArray.put(TOKEN_13)
        jsonArray.put(TOKEN_14)
        firebasePush.sendToGroup(jsonArray)

    }




    private fun observe() {
        viewModel.isLoading.observe(this) {
            if (it) {
                binding.fabPrevious.visibility = View.GONE
                binding.fabDone.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                binding.progressBar.show()
            } else {
                binding.progressBar.visibility = View.GONE
                binding.progressBar.hide()

            }
        }
        viewModel.addImage.observe(this) { it1 ->
            if (it1 != null) {
                Toast.makeText(this, "Upload Image ${it1}", Toast.LENGTH_SHORT).show()

            }
        }
        viewModel.addFileMp3.observe(this) { it2 ->
            if (it2 != null) {
                Toast.makeText(this, "Upload File Mp3 ${it2}", Toast.LENGTH_SHORT).show()

            }
        }

        viewModel.addSongStatus.observe(this) {
            Log.d("ABC Upload",it.toString())
            if (it != null) {

                Toast.makeText(this, "Upload Song Successfully ", Toast.LENGTH_SHORT).show()
                send(viewModel.songName, viewModel.singerName)
                onBackPressedDispatcher.onBackPressed()

            }
        }

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
        } else {
            viewModel.addImageAndFile()
            viewModel.addFileMp3.observe(this) { it1 ->
                if (it1 != null) {
                    viewModel.addImage.observe(this) { it2 ->
                        val song = SongPost(viewModel.songName,viewModel.singerName,it2.toString(),viewModel.lyrics,it1.toString())
                        viewModel.addSong(song)
                    }
                }
            }

        }

    }


}