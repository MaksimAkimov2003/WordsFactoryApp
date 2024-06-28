package com.akimov.wordsfactory.di

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import com.akimov.data.statistics.StatRepositoryImpl
import com.akimov.domain.statistics.repository.StatRepository
import com.akimov.domain.statistics.useCase.SetUserStudiedTodayUseCase
import com.akimov.domain.statistics.useCase.ShouldShowNotificationUseCase
import com.akimov.wordsfactory.notifications.AlarmScheduler
import com.akimov.wordsfactory.notifications.AppNotifications
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val notificationsModule = module {
    single {
        AppNotifications(
            context = androidContext(),
            notificationManager = androidContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        )
    }

    single {
        AlarmScheduler(
            context = androidContext(),
            alarmManager = androidContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        )
    }

    factoryOf(::SetUserStudiedTodayUseCase)
    factoryOf(::ShouldShowNotificationUseCase)

    factory<StatRepository> {
        StatRepositoryImpl(
            sharedPreferences = androidContext().getSharedPreferences(
                "statistics",
                Context.MODE_PRIVATE
            )
        )
    }

}
