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
    var petId: String,

    @ColumnInfo(name = "petName")
    var petName: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "hour")
    var hour: String,

    @ColumnInfo(name = "minutes")
    var minutes: String,

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
