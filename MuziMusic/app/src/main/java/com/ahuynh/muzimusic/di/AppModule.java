package com.ahuynh.muzimusic.di;

import com.ahuynh.muzimusic.ui.component.song.SongRepository;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.disposables.Disposable;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Provides
    public SongRepository provideSongRepository() {
        return new SongRepository();
    }

    @Provides
    public Disposable provideDisposable() {
        // Provide your Disposable instance here
        return new Disposable() {
            @Override
            public void dispose() {
                // Dispose logic
            }

            @Override
            public boolean isDisposed() {
                // IsDisposed logic
                return false;
            }
        };
    }
}
