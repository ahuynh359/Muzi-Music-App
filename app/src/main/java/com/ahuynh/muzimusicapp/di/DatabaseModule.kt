package com.ahuynh.muzimusicapp.di

import android.content.Context
import androidx.room.Room
import com.ahuynh.muzimusicapp.data.database.AppDatabase
import com.ahuynh.muzimusicapp.data.database.dao.SearchHistoryDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDB(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "muzi_music_db").build()
    }


//
//    @Provides
//    fun provideSongDao(appDB: AppDatabase): SongHistoryDAO {
//        return appDB.songDao()
//    }
//
//    @Provides
//    fun provideSingerDao(appDB: AppDatabase): SingerHistoryDAO {
//        return appDB.singerDao()
//    }
//
//    @Provides
//    fun provideAlbumDao(appDB: AppDatabase): AlbumHistoryDAO {
//        return appDB.albumDao()
//    }
//
//
//    @Provides
//    fun provideNotificationDao(appDB: AppDatabase): NotificationDAO {
//        return appDB.notificationDao()
//    }

    @Provides
    fun provideSearchHistoryDao(appDB: AppDatabase): SearchHistoryDAO {
        return appDB.searchHistoryDao()
    }
}