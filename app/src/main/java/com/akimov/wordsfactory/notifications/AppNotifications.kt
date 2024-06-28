package com.akimov.wordsfactory.notifications

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import com.akimov.wordsfactory.R

class AppNotifications(
    private val notificationManager: NotificationManager,
    private val context: Context
) {
    private companion object {
        const val CHANNEL = "WORDS_FACTORY_CHANNEL"
        const val NOTIFICATION_ID = 1
    }

    @SuppressLint("WrongConstant")
    fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            /* id = */
            CHANNEL,
            /* name = */
            context.getText(R.string.app_name),
            /* importance = */
            NotificationManager.IMPORTANCE_MAX
        )
        notificationChannel.description = context.getText(R.string.time_to_study).toString()
        notificationManager.createNotificationChannel(notificationChannel)
    }

    fun setNotification() {
        Log.d("CommonTag", "setNotification")
        val notification = Notification.Builder(
            /* context = */
            context,
            /* channelId = */
            CHANNEL
        )
            .setContentTitle(context.getText(R.string.app_name))
            .setContentText(context.getText(R.string.time_to_study))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(Notification.PRIORITY_MAX)
            .build()

        notificationManager.notify(
            /* id = */
            NOTIFICATION_ID,
            /* notification = */
            notification
        )
    }
}
