package com.ahuynh.muzimusicapp.ui.component.activity.main.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.ahuynh.muzimusicapp.adapter.ViewPagerAdapter
import com.ahuynh.muzimusicapp.databinding.ActivitySearchBinding
import com.ahuynh.muzimusicapp.ui.base.BaseActivity
import com.ahuynh.muzimusicapp.ui.component.activity.main.search.album.AlbumSearchFragment
import com.ahuynh.muzimusicapp.ui.component.activity.main.search.song.SongSearchFragment
import com.ahuynh.muzimusicapp.ui.component.activity.main.search.singer.SingerSearchFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate) {
    private val viewModel by viewModels<SearchViewModel>()

    companion object {
        const val TAG = "SearchActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleUI()

        observe()

    }

    private fun observe() {
        viewModel.isLoading.observe(this) {
            binding.pgLoading.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.isSearchDone.observe(this) {
            if (it) {
                binding.rcyHistory.visibility = View.GONE
                binding.searchHistory.visibility = View.GONE
                binding.tabLayout.visibility = View.VISIBLE
                binding.viewPager.visibility = View.VISIBLE
            }
        }
    }

    private fun handleUI() {
        binding.tvCancle.setOnClickListener {
            finish()
        }


        binding.edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val str = binding.edtSearch.text.toString().trim()
                if (str.isNotEmpty()) {
                    viewModel.search(str)
                }
            }
            true
        }

        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val fragmentList = arrayListOf(
            SongSearchFragment(),
            AlbumSearchFragment(),
            SingerSearchFragment()
        )
        binding.viewPager.adapter = ViewPagerAdapter(fragmentList, this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Songs"
                }

                1 -> {
                    tab.text = "Albums"
                }

                2 -> {
                    tab.text = "Singers"
                }
            }
        }.attach()
    }

    override fun getSnackbarView(): View {
        return binding.main
    }
}
