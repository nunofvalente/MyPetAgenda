package com.nunovalente.android.mypetagenda.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nunovalente.android.mypetagenda.data.entities.DatabaseNote
import com.nunovalente.android.mypetagenda.data.entities.DatabasePet
import com.nunovalente.android.mypetagenda.data.entities.DatabaseReminder
import com.nunovalente.android.mypetagenda.data.local.notes.NoteDao
import com.nunovalente.android.mypetagenda.data.local.pets.PetDao
import com.nunovalente.android.mypetagenda.data.local.reminders.ReminderDao

@Database(entities = [DatabasePet::class, DatabaseReminder::class, DatabaseNote::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun petDao(): PetDao
    abstract fun reminderDao(): ReminderDao
    abstract fun noteDao(): NoteDao
}