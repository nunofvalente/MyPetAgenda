package com.nunovalente.android.mypetagenda.dependencyinjection.application

import androidx.room.Room
import com.nunovalente.android.mypetagenda.util.Constants
import com.nunovalente.android.mypetagenda.application.MyApplication
import com.nunovalente.android.mypetagenda.data.PetRepository
import com.nunovalente.android.mypetagenda.data.Repository
import com.nunovalente.android.mypetagenda.data.local.*
import com.nunovalente.android.mypetagenda.data.local.dao.NoteDao
import com.nunovalente.android.mypetagenda.data.local.dao.PetDao
import com.nunovalente.android.mypetagenda.data.local.dao.ReminderDao
import com.nunovalente.android.mypetagenda.data.local.db.Database
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @AppScope
    @Provides
    fun database(application: MyApplication): Database = Room.databaseBuilder(
        application.applicationContext,
        Database::class.java,
        Constants.DATABASE_NAME
    ).build()


    @Provides
    fun petDao(database: Database): PetDao = database.petDao()

    @Provides
    fun reminderDao(database: Database): ReminderDao = database.reminderDao()

    @Provides
    fun noteDao(database: Database): NoteDao = database.noteDao()

    @Provides
    fun petLocalDataSource(dao: PetDao): PetDataSource = PetLocalDataSource(dao)

    @Provides
    fun repository(petLocalDataSource: PetLocalDataSource): PetRepository = Repository(petLocalDataSource)
}