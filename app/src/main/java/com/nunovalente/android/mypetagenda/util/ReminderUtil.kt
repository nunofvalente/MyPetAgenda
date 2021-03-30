package com.nunovalente.android.mypetagenda.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.nunovalente.android.mypetagenda.models.Reminder
import com.nunovalente.android.mypetagenda.models.getRecurringDays
import com.nunovalente.android.mypetagenda.notif.ReminderBroadcastReceiver
import com.nunovalente.android.mypetagenda.util.Constants.FRIDAY
import com.nunovalente.android.mypetagenda.util.Constants.MONDAY
import com.nunovalente.android.mypetagenda.util.Constants.PET_NAME
import com.nunovalente.android.mypetagenda.util.Constants.RECURRING
import com.nunovalente.android.mypetagenda.util.Constants.SATURDAY
import com.nunovalente.android.mypetagenda.util.Constants.SUNDAY
import com.nunovalente.android.mypetagenda.util.Constants.THURSDAY
import com.nunovalente.android.mypetagenda.util.Constants.TITLE
import com.nunovalente.android.mypetagenda.util.Constants.TUESDAY
import com.nunovalente.android.mypetagenda.util.Constants.WEDNESDAY
import java.util.*


class ReminderUtil {

    companion object {

        @JvmStatic
        fun scheduleReminder(context: Context, reminder: Reminder, alarmManager: AlarmManager) {
            val intent = Intent(context, ReminderBroadcastReceiver::class.java).apply {
                putExtra(RECURRING, reminder.isRecurring)
                putExtra(MONDAY, reminder.monday)
                putExtra(TUESDAY, reminder.tuesday)
                putExtra(WEDNESDAY, reminder.wednesday)
                putExtra(THURSDAY, reminder.thursday)
                putExtra(FRIDAY, reminder.friday)
                putExtra(SATURDAY, reminder.saturday)
                putExtra(SUNDAY, reminder.sunday)
                putExtra(PET_NAME, reminder.petName)
                putExtra(TITLE, reminder.title)
            }

            val pendingIntent = PendingIntent.getBroadcast(context, reminder.id, intent, 0)

            val calendar = Calendar.getInstance()

            var dayOfMonth = 0
            var month = 0
            if(reminder.date != "") {
                 dayOfMonth = reminder.date.substring(0, 2).toInt()
                 month = reminder.date.substring(3, 5).toInt() - 1
            }

            calendar.timeInMillis = System.currentTimeMillis()
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.HOUR_OF_DAY, reminder.hour)
            calendar.set(Calendar.MINUTE, reminder.minutes)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            if (calendar.timeInMillis <= System.currentTimeMillis()) {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
            }

            if (!reminder.isRecurring) {
                var toastString: String? = null
                try {
                    toastString = String.format("Alarm for %s has been set", reminder.title)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                Toast.makeText(context, toastString, Toast.LENGTH_SHORT).show()

                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )

            } else {
                val toastText = String.format(
                    "Recurring Alarm %s scheduled for %s",
                    reminder.title,
                    reminder.getRecurringDays(),
                )
                Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()

                val RUN_DAILY = (24 * 60 * 60 * 1000).toLong()
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    RUN_DAILY,
                    pendingIntent
                )
            }
            reminder.isStarted = true
        }

        @JvmStatic
        fun cancelReminder(context: Context, reminder: Reminder) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, ReminderBroadcastReceiver::class.java)
            val reminderPendingIntent = PendingIntent.getBroadcast(context, reminder.id, intent, 0)
            alarmManager.cancel(reminderPendingIntent)
            reminder.isStarted = false
            val toastText = String.format(
                "Alarm cancelled for %02d:%02d",
                reminder.hour,
                reminder.minutes,
            )
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        }
    }
}