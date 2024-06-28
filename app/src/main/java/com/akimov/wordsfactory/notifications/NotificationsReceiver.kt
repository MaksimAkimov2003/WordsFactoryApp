package com.akimov.wordsfactory.notifications

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.impl.utils.ForceStopRunnable.BroadcastReceiver
import com.akimov.domain.statistics.useCase.ShouldShowNotificationUseCase
import org.koin.java.KoinJavaComponent.inject

class NotificationsReceiver : BroadcastReceiver() {
    private val shouldShowNotificationUseCase: ShouldShowNotificationUseCase by inject(
        ShouldShowNotificationUseCase::class.java
    )
    private val appNotifications: AppNotifications by inject(AppNotifications::class.java)

    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("CommonTag", "onReceive")
        super.onReceive(context, intent)

        val shouldShowNotification = shouldShowNotificationUseCase()
        Log.d("CommonTag", "shouldShowNotification: $shouldShowNotification")
        if (shouldShowNotification) {
            appNotifications.setNotification()
        }
    }
}