package com.nunovalente.android.mypetagenda.models

import android.os.Parcelable
import com.nunovalente.android.mypetagenda.data.entities.DatabaseReminder
import kotlinx.parcelize.Parcelize


@Parcelize
data class Reminder(
    var id: Int,
    var petId: Int,
    var petName: String,
    var title: String,
    var hour: Int,
    var minutes: Int,
    var isStarted: Boolean,
    var isRecurring: Boolean,
    var monday: Boolean,
    var tuesday: Boolean,
    var wednesday: Boolean,
    var thursday: Boolean,
    var friday: Boolean,
    var saturday: Boolean,
    var sunday: Boolean,
    var date: String
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