package com.nunovalente.android.mypetagenda.dependencyinjection.application

import androidx.room.Room
import com.nunovalente.android.mypetagenda.util.Constants
import com.nunovalente.android.mypetagenda.application.MyApplication
import com.nunovalente.android.mypetagenda.data.*
import com.nunovalente.android.mypetagenda.data.local.notes.NoteDao
import com.nunovalente.android.mypetagenda.data.local.pets.PetDao
import com.nunovalente.android.mypetagenda.data.local.reminders.ReminderDao
import com.nunovalente.android.mypetagenda.data.local.db.Database
import com.nunovalente.android.mypetagenda.data.local.notes.NoteDataSource
import com.nunovalente.android.mypetagenda.data.local.notes.NoteLocalDataSource
import com.nunovalente.android.mypetagenda.data.local.pets.PetDataSource
import com.nunovalente.android.mypetagenda.data.local.pets.PetLocalDataSource
import com.nunovalente.android.mypetagenda.data.local.reminders.ReminderDataSource
import com.nunovalente.android.mypetagenda.data.local.reminders.ReminderLocalDataSource
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
    ).fallbackToDestructiveMigration()
        .build()


    @Provides
    fun petDao(database: Database): PetDao = database.petDao()

    @Provides
    fun reminderDao(database: Database): ReminderDao = database.reminderDao()

    @Provides
    fun noteDao(database: Database): NoteDao = database.noteDao()

    @Provides
    fun petLocalDataSource(dao: PetDao): PetDataSource = PetLocalDataSource(dao)

    @Provides
    fun noteLocalDataSource(dao: NoteDao): NoteDataSource = NoteLocalDataSource(dao)

    @Provides
    fun reminderLocalDataSource(dao: ReminderDao): ReminderDataSource = ReminderLocalDataSource(dao)

    @Provides
    fun repository(petLocalDataSource: PetLocalDataSource): PetRepository = DefaultPetRepository(petLocalDataSource)

    @Provides
    fun noteRepository(noteLocalDataSource: NoteLocalDataSource): NoteRepository = DefaultNoteRepository(noteLocalDataSource)

    @Provides
    fun reminderRepository(reminderLocalDataSource: ReminderLocalDataSource): ReminderRepository = DefaultReminderRepository(reminderLocalDataSource)
}