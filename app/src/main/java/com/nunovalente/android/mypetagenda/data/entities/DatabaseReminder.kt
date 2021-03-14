package com.nunovalente.android.mypetagenda.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminder_able")
data class DatabaseReminder(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "petId")
    val petId: String,

    @ColumnInfo(name = "petName")
    val petName: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "hour")
    val hour: String,

    @ColumnInfo(name = "minutes")
    val minutes: String,

    @ColumnInfo(name = "isRecurring")
    val isRecurring: Boolean
)
