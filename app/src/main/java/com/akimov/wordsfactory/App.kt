package com.akimov.wordsfactory

import android.app.Application
import com.akimov.wordsfactory.di.authModule
import com.akimov.wordsfactory.di.dictionaryModule
import com.akimov.wordsfactory.di.ioModule
import com.akimov.wordsfactory.di.onBoardingModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                authModule,
                dictionaryModule,
                ioModule,
                onBoardingModule
            )
        }
    }
}