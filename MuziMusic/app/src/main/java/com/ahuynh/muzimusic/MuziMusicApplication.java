package com.ahuynh.muzimusic;

import android.app.Application;
import android.content.Context;


public class MuziMusicApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return MuziMusicApplication.context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MuziMusicApplication.context = getApplicationContext();
    }
}
