package com.nunovalente.android.mypetagenda.models

import android.app.PendingIntent
import android.content.Intent
import android.os.Parcelable
import com.nunovalente.android.mypetagenda.data.entities.DatabaseReminder
import kotlinx.parcelize.Parcelize


@Parcelize
data class Reminder(
    var id: Int = 0,
    var petId: Int = 0,
    var petName: String = "",
    var title: String = "",
    var hour: Int = 0,
    var minutes: Int = 0,
    var isStarted: Boolean = false,
    var isRecurring: Boolean = false,
    var monday: Boolean = false,
    var tuesday: Boolean = false,
    var wednesday: Boolean = false,
    var thursday: Boolean = false,
    var friday: Boolean = false,
    var saturday: Boolean = false,
    var sunday: Boolean = false,
    var date: String = ""
) : Parcelable

fun Reminder.toDatabaseModel(): DatabaseReminder {
    return DatabaseReminder(
        id = this.id,
        petId = this.petId,
        petName = this.petName,
        title = this.title,
        hour = this.hour,
        minutes = this.minutes,
        isStarted = this.isStarted,
        isRecurring = this.isRecurring,
        monday = this.monday,
        tuesday = this.tuesday,
        wednesday = this.wednesday,
        thursday = this.thursday,
        friday = this.friday,
        saturday = this.saturday,
        sunday = this.sunday,
        date = this.date
    )
}

fun Reminder.getRecurringDays(): String {
    if (!isRecurring) {
        return ""
    }

    var days = ""
    if (monday) {
        days += "Monday "
    }
    if (tuesday) {
        days += "Tuesday "
    }
    if (wednesday) {
        days += "Wednesday "
    }
    if (thursday) {
        days += "Thursday "
    }
    if (friday) {
        days += "Friday "
    }
    if (saturday) {
        days += "Saturday "
    }
    if (sunday) {
        days += "Sunday "
    }
    return days
}