package com.ahuynh.muzimusic.ui.component;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;

import com.ahuynh.muzimusic.R;
import com.ahuynh.muzimusic.databinding.ActivityMainBinding;
import com.ahuynh.muzimusic.ui.component.album.AlbumFragment;
import com.ahuynh.muzimusic.ui.component.artist.ArtistFragment;
import com.ahuynh.muzimusic.ui.component.chart.ChartFragment;
import com.ahuynh.muzimusic.ui.component.song.SongFragment;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        //Không cho theme tối
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpBottomNav();

    }
    
    private void setUpBottomNav() {

        binding.btmNav.setOnItemSelectedListener(menuItem -> {
            Fragment currentFragment = null;
            int itemId = menuItem.getItemId();

            if (itemId == R.id.songFragment) {
                currentFragment = new SongFragment();
            } else if (itemId == R.id.albumFragment) {
                currentFragment = new AlbumFragment();
            } else if (itemId == R.id.artistFragment) {
                currentFragment = new ArtistFragment();
            } else if (itemId == R.id.chartFragment) {
                currentFragment = new ChartFragment();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, currentFragment).commit();
            return true;
        });
    }


}