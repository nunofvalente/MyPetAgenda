package com.nunovalente.android.mypetagenda.models

import android.os.Parcelable
import com.nunovalente.android.mypetagenda.data.entities.DatabaseReminder
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reminder(
    val id: Int,
    val petId: String,
    val petName: String,
    val title: String,
    val hour: String,
    val minutes: String,
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

fun DatabaseReminder.toDatabaseModel(): DatabaseReminder {
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