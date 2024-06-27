package com.akimov.wordsfactory

import android.app.Application
import androidx.work.WorkManager
import com.akimov.wordsfactory.di.authModule
import com.akimov.wordsfactory.di.dictionaryModule
import com.akimov.wordsfactory.di.ioModule
import com.akimov.wordsfactory.di.onBoardingModule
import com.akimov.wordsfactory.di.trainingModule
import com.akimov.wordsfactory.di.videoModule
import com.akimov.wordsfactory.di.widgetModule
import com.akimov.wordsfactory.di.workersModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    private val workManager: WorkManager by inject()

    override fun onCreate() {
        super.onCreate()
        initKoin()
        setWidgetUpdate()
    }

    private fun setWidgetUpdate() {
//        val request = PeriodicWorkRequestBuilder<UpdateWidgetWorker>(
//            repeatInterval = UiConstants.WIDGET_UPDATE_INTERVAL_SECONDS,
//            repeatIntervalTimeUnit = TimeUnit.SECONDS
//        ).build()
//        workManager.enqueue(request)
    }

    private fun initKoin() {
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
                widgetModule,
                workersModule
            )
            workManagerFactory()
        }
    }
}
