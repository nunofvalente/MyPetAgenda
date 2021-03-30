package com.nunovalente.android.mypetagenda.notif

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import com.nunovalente.android.mypetagenda.MainActivity
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.models.Reminder
import com.nunovalente.android.mypetagenda.util.Constants
import com.nunovalente.android.mypetagenda.util.Constants.CHANNEL_ID
import com.nunovalente.android.mypetagenda.util.Constants.PET_ID
import com.nunovalente.android.mypetagenda.util.Constants.PET_NAME
import com.nunovalente.android.mypetagenda.util.Constants.REMINDER_DISMISS


@Suppress("DEPRECATION")
class ReminderService: Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var vibrator: Vibrator

    override fun onCreate() {
        super.onCreate()

        mediaPlayer = MediaPlayer.create(this, R.raw.leapfrog);
        mediaPlayer.isLooping = true;

        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationIntent = Intent(this, MainActivity::class.java).apply {
            setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        val dismissIntent = Intent(this, ReminderBroadcastReceiver::class.java).apply {
            action = ACTION_DISMISS
            putExtra(REMINDER_DISMISS, 0)
            putExtra(PET_ID, intent?.getIntExtra(PET_ID, -1))
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val dismissPendingIntent = PendingIntent.getBroadcast(this, 0, dismissIntent, 0)

        val alarmTitle = String.format("%s Alarm", intent?.getStringExtra(PET_NAME))
        val alarmText = intent?.getStringExtra(Constants.TITLE)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(alarmTitle)
            .setContentText(alarmText)
            .setSmallIcon(R.drawable.ic_paw)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .addAction(R.drawable.ic_alarm_off, getString(R.string.dismiss), dismissPendingIntent)
            .build()

        mediaPlayer.start()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }

        startForeground(1, notification)

        return START_STICKY

    }

    override fun onDestroy() {
        super.onDestroy()

        mediaPlayer.stop()
        vibrator.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        private const val ACTION_DISMISS = "dismiss"
    }
}