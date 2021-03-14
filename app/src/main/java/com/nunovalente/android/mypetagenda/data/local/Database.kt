package com.nunovalente.android.mypetagenda.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nunovalente.android.mypetagenda.data.entities.DatabasePet
import com.nunovalente.android.mypetagenda.data.entities.DatabaseReminder

@Database(entities = [DatabasePet::class, DatabaseReminder::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun petDao(): PetDao
    abstract fun reminderDao(): ReminderDao
}