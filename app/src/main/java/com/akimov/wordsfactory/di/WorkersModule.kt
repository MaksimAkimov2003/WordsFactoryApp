package com.akimov.wordsfactory.di

import androidx.work.WorkManager
import com.akimov.wordsfactory.worker.UpdateWidgetWorker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val workersModule = module {
    single<WorkManager> { WorkManager.getInstance(androidContext()) }

    worker {
        UpdateWidgetWorker(
            context = androidContext(),
            parameters = get()
        )
    }
}
