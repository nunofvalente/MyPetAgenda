package com.nunovalente.android.mypetagenda.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nunovalente.android.mypetagenda.data.entities.DatabasePet

@Database(entities = [DatabasePet::class], version = 1, exportSchema = false)
abstract class PetDatabase : RoomDatabase() {

    abstract fun petDao(): PetDao
}