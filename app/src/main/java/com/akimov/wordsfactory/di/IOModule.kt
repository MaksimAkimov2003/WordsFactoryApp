package com.akimov.wordsfactory.di

import android.media.MediaPlayer
import androidx.room.Room
import com.akimov.wordsfactory.RoomDb
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val ioModule = module {
    // Media
    single<MediaPlayer> { MediaPlayer() }

    // Network
    single<OkHttpClient> {
        OkHttpClient()
            .newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/v2/")
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Database
    single<RoomDb> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = RoomDb::class.java,
            name = "room_db"
        ).build()
    }
}