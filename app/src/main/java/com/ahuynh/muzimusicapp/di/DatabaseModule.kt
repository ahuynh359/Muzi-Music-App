package com.ahuynh.muzimusicapp.di

import android.content.Context
import androidx.room.Room
import com.ahuynh.muzimusicapp.data.database.AppDatabase
import com.ahuynh.muzimusicapp.data.database.dao.AlbumDao
import com.ahuynh.muzimusicapp.data.database.dao.SearchHistoryDao
import com.ahuynh.muzimusicapp.data.database.dao.SingerDao
import com.ahuynh.muzimusicapp.data.database.dao.SongDao
import com.ahuynh.muzimusicapp.data.database.dao.TypeDao
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
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "abc_db").build()
    }


    @Provides
    fun provideSongDao(appDB: AppDatabase): SongDao {
        return appDB.songDao()
    }

    @Provides
    fun provideSingerDao(appDB: AppDatabase): SingerDao {
        return appDB.singerDao()
    }

    @Provides
    fun provideAlbumDao(appDB: AppDatabase): AlbumDao {
        return appDB.albumDao()
    }

    @Provides
    fun provideTypeDao(appDB: AppDatabase): TypeDao {
        return appDB.typeDao()
    }


    @Provides
    fun provideSearchHistoryDao(appDB: AppDatabase): SearchHistoryDao {
        return appDB.searchHistoryDao()
    }
}