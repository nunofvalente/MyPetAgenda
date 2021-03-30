package com.nunovalente.android.mypetagenda.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.util.Constants.CHANNEL_ID

/**
 * Creates a notification channel for our Reminder Notifications
 */
fun createChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.reminders),

            NotificationManager.IMPORTANCE_HIGH
        )
            .apply {
                setShowBadge(true)
            }

        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.description =
            context.getString(R.string.reminder_channel_notification)

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)
    }
}