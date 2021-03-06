package com.nunovalente.android.mypetagenda.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nunovalente.android.mypetagenda.models.Reminder

@Entity(tableName = "reminder_table")
data class DatabaseReminder(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "petId")
    var petId: Int,

    @ColumnInfo(name = "petName")
    var petName: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "hour")
    var hour: Int,

    @ColumnInfo(name = "minutes")
    var minutes: Int,

    @ColumnInfo(name = "isStarted")
    var isStarted: Boolean,

    @ColumnInfo(name = "isRecurring")
    var isRecurring: Boolean,

    @ColumnInfo(name = "monday")
    var monday: Boolean,

    @ColumnInfo(name = "tuesday")
    var tuesday: Boolean,

    @ColumnInfo(name = "wednesday")
    var wednesday: Boolean,

    @ColumnInfo(name = "thursday")
    var thursday: Boolean,

    @ColumnInfo(name = "friday")
    var friday: Boolean,

    @ColumnInfo(name = "saturday")
    var saturday: Boolean,

    @ColumnInfo(name = "sunday")
    var sunday: Boolean,

    @ColumnInfo(name = "date")
    var date: String
)

fun DatabaseReminder.toDomainModel(): Reminder {
    return Reminder(
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

fun List<DatabaseReminder>.toDomainModel(): List<Reminder> {
    return map {
        Reminder(
            id = it.id,
            petId = it.petId,
            petName = it.petName,
            title = it.title,
            hour = it.hour,
            minutes = it.minutes,
            isStarted = it.isStarted,
            isRecurring = it.isRecurring,
            monday = it.monday,
            tuesday = it.tuesday,
            wednesday = it.wednesday,
            thursday = it.thursday,
            friday = it.friday,
            saturday = it.saturday,
            sunday = it.sunday,
            date = it.date
        )
    }
}
