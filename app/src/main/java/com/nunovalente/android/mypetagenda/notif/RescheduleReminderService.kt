package com.nunovalente.android.mypetagenda.notif

import android.app.AlarmManager
import android.content.Intent
import androidx.lifecycle.LifecycleService
import com.nunovalente.android.mypetagenda.application.MyApplication
import com.nunovalente.android.mypetagenda.data.DefaultReminderRepository
import com.nunovalente.android.mypetagenda.util.ReminderUtil
import javax.inject.Inject

class RescheduleReminderService : LifecycleService() {

    @Inject
    lateinit var repository: DefaultReminderRepository
    @Inject
    lateinit var alarmManager: AlarmManager

    override fun onCreate() {
        super.onCreate()

        (applicationContext as MyApplication).appComponent.inject(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

       repository.getAllReminders().observe(this, { reminderList ->
            for(reminder in reminderList) {
                if(reminder.isStarted) {
                    ReminderUtil.scheduleReminder(this, reminder, alarmManager)
                }
            }
        })

        return START_STICKY
    }
}