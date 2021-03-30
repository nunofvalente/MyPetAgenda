package com.nunovalente.android.mypetagenda.notif

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.nunovalente.android.mypetagenda.application.MyApplication
import com.nunovalente.android.mypetagenda.data.ReminderRepository
import com.nunovalente.android.mypetagenda.data.Result
import com.nunovalente.android.mypetagenda.util.Constants
import com.nunovalente.android.mypetagenda.util.Constants.PET_ID
import com.nunovalente.android.mypetagenda.util.Constants.PET_NAME
import com.nunovalente.android.mypetagenda.util.Constants.RECURRING
import com.nunovalente.android.mypetagenda.util.Constants.TITLE
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

class ReminderBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if(Intent.ACTION_BOOT_COMPLETED == intent?.action) {
            val toastText = String.format("Alarm Reboot")
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            startRescheduleAlarmService(context)

        } else if (intent?.action == ACTION_DISMISS) {
            val intentService = Intent(context, ReminderService::class.java)
            context?.stopService(intentService)

        } else {
            val toastText = String.format("Alarm Received")
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            if(!intent?.getBooleanExtra(RECURRING, false)!!) {
                startReminderService(context, intent)
            }
            if(alarmIsToday(intent)) {
                startReminderService(context, intent)
            }
        }
    }

    private fun startRescheduleAlarmService(context: Context?) {
        val intentService = Intent(context, RescheduleReminderService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context!!.startForegroundService(intentService)
        } else {
            context!!.startService(intentService)
        }
    }

    private fun startReminderService(context: Context?, intent: Intent?) {
        val intentService = Intent(context, ReminderService::class.java).apply {
            putExtra(TITLE, intent?.getStringExtra(TITLE))
            putExtra(PET_NAME, intent?.getStringExtra(PET_NAME))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context!!.startForegroundService(intentService)
        } else {
            context!!.startService(intentService)
        }
    }

    private fun alarmIsToday(intent: Intent?): Boolean {
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        if(intent != null) {

            when (calendar.get(Calendar.DAY_OF_WEEK)) {
                Calendar.MONDAY -> {
                    return intent.getBooleanExtra(Constants.MONDAY, false)
                }
                Calendar.TUESDAY -> {
                    return intent.getBooleanExtra(Constants.TUESDAY, false)
                }
                Calendar.WEDNESDAY -> {
                    return intent.getBooleanExtra(Constants.WEDNESDAY, false)
                }
                Calendar.THURSDAY -> {
                    return intent.getBooleanExtra(Constants.THURSDAY, false)
                }
                Calendar.FRIDAY -> {
                    return intent.getBooleanExtra(Constants.FRIDAY, false)
                }
                Calendar.SATURDAY -> {
                    return intent.getBooleanExtra(Constants.SATURDAY, false)
                }
                Calendar.SUNDAY -> {
                    return intent.getBooleanExtra(Constants.SUNDAY, false)
                }
            }
        }
        return false
    }

    companion object {
        private const val ACTION_DISMISS = "dismiss"
    }
}

