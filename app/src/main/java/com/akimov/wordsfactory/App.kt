package com.akimov.wordsfactory

import android.app.Application
import com.akimov.wordsfactory.di.authModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                authModule
            )
        }
    }
}