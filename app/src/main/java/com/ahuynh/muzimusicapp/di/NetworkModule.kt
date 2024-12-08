package com.ahuynh.muzimusicapp.di

import com.ahuynh.muzimusicapp.data.api.AlbumAPI
import com.ahuynh.muzimusicapp.data.api.AuthAPI
import com.ahuynh.muzimusicapp.data.api.CommentAPI
import com.ahuynh.muzimusicapp.data.api.NotificationAPI
import com.ahuynh.muzimusicapp.data.api.PlaylistAPI
import com.ahuynh.muzimusicapp.data.api.SingerAPI
import com.ahuynh.muzimusicapp.data.api.SongAPI
import com.ahuynh.muzimusicapp.data.api.TypeAPI
import com.ahuynh.muzimusicapp.data.api.UserAPI
import com.ahuynh.muzimusicapp.utils.Constants.BASE_URL
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    @Singleton
    fun provideAuthInterceptor(pref : SharePreferencesHelper): Interceptor {
        return Interceptor { chain ->

            val request = chain.request().newBuilder()
                .header("Accept", "application/json")
                .header("Authorization", "Bearer ${pref.getToken()}")
                .build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(): MoshiConverterFactory {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    @Singleton
    fun provideOKHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.interceptors().add(httpLoggingInterceptor)
        builder.addInterceptor(authInterceptor)
        builder.callTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES)
        return builder.build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit
            .Builder()
            .addConverterFactory(moshiConverterFactory)
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideAuthAPI(retrofit: Retrofit): AuthAPI {
        return retrofit.create(AuthAPI::class.java)
    }
    @Provides
    fun provideAlbumAPI(retrofit: Retrofit): AlbumAPI {
        return retrofit.create(AlbumAPI::class.java)
    }

    @Provides
    fun provideSongAPI(retrofit: Retrofit): SongAPI {
        return retrofit.create(SongAPI::class.java)
    }

    @Provides
    fun provideTypeAPI(retrofit: Retrofit): TypeAPI {
        return retrofit.create(TypeAPI::class.java)
    }

    @Provides
    fun provideUserAPI(retrofit: Retrofit): UserAPI {
        return retrofit.create(UserAPI::class.java)

    }

    @Provides
    fun providePlaylistAPI(retrofit: Retrofit): PlaylistAPI {
        return retrofit.create(PlaylistAPI::class.java)
    }


    @Provides
    fun provideSingerAPI(retrofit: Retrofit): SingerAPI {
        return retrofit.create(SingerAPI::class.java)
    }

    @Provides
    fun provideCommentAPI(retrofit: Retrofit): CommentAPI {
        return retrofit.create(CommentAPI::class.java)
    }

    @Provides
    fun provideNotificationAPI(retrofit: Retrofit): NotificationAPI {
        return retrofit.create(NotificationAPI::class.java)
    }
}