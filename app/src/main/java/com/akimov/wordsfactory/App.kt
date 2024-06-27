package com.akimov.wordsfactory

import android.app.Application
import com.akimov.wordsfactory.di.authModule
import com.akimov.wordsfactory.di.dictionaryModule
import com.akimov.wordsfactory.di.ioModule
import com.akimov.wordsfactory.di.onBoardingModule
import com.akimov.wordsfactory.di.trainingModule
import com.akimov.wordsfactory.di.videoModule
import com.akimov.wordsfactory.di.widgetModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                authModule,
                dictionaryModule,
                ioModule,
                onBoardingModule,
                trainingModule,
                videoModule,
                widgetModule
            )
        }
    }
}
