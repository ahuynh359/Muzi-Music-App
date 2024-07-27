package com.ahuynh.muzimusicapp.ui.component.admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.AdminViewPagerAdapter
import com.ahuynh.muzimusicapp.databinding.ActivityAdminBinding
import com.ahuynh.muzimusicapp.ui.base.activity.BaseActivity
import com.ahuynh.muzimusicapp.ui.component.auth.AuthActivity
import com.ahuynh.muzimusicapp.ui.component.user.profile.ProfileFragmentDirections
import com.ahuynh.muzimusicapp.ui.component.user.setting.SettingViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminActivity : BaseActivity<ActivityAdminBinding>(ActivityAdminBinding::inflate) {

    companion object {
        const val TAG = "AdminActivity"
    }

    private val viewModel by viewModels<AdminViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleUI()

    }

    private fun handleUI() {
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val navGraphIds = listOf(
            R.navigation.manage_user_graph,
            R.navigation.manage_song_graph,
            R.navigation.manage_type_graph,
            R.navigation.manage_album_graph,
            R.navigation.manage_singer_graph,
            R.navigation.manage_comment_graph,

            )


        val pagerAdapter = AdminViewPagerAdapter(this, navGraphIds)
        binding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Users"
                }

                1 -> {
                    tab.text = "Songs"
                }

                2 -> {
                    tab.text = "Types"
                }

                3 -> {
                    tab.text = "Albums"
                }

                4 -> {
                    tab.text = "Singers"
                }

                5 -> {
                    tab.text = "Comments"
                }
            }
        }.attach()
        binding.btnLogOut.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(this@AdminActivity, AuthActivity::class.java))
            finish()
        }

    }

    override fun getSnackbarView(): View {
        return binding.main
    }




}