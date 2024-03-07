package com.ahuynh.muzimusic.ui.component;

import static com.ahuynh.muzimusic.utils.Contants.PERMISSION_REQUEST_CODE;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ahuynh.muzimusic.MainViewModel;
import com.ahuynh.muzimusic.R;
import com.ahuynh.muzimusic.databinding.ActivityMainBinding;
import com.ahuynh.muzimusic.ui.component.album.AlbumFragment;
import com.ahuynh.muzimusic.ui.component.artist.ArtistFragment;
import com.ahuynh.muzimusic.ui.component.chart.ChartFragment;
import com.ahuynh.muzimusic.ui.component.song.SongFragment;
import com.ahuynh.muzimusic.utils.PermissionHelper;


@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private Fragment currentFragment;
    private MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        //Không cho theme tối
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);


        setUpBottomNav();

    }

    private void checkAndRequestPermissions() {
        if (!PermissionHelper.hasPermissions(MainActivity.this)) {
            PermissionHelper.requestPermissions(MainActivity.this);
        }

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permission + " granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, permission + " denied", Toast.LENGTH_SHORT).show();
                    showSettingsDialog();
                }
            }
        }
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permission deny");
        builder.setMessage("Open setting for permission");
        builder.setPositiveButton("OK", (dialog, which) -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        });
        builder.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkAndRequestPermissions();
    }

    private void setUpBottomNav() {

        currentFragment = new SongFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, currentFragment).commit();

        binding.btmNav.setOnItemSelectedListener(menuItem -> {

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