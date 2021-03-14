package com.nunovalente.android.mypetagenda.dependencyinjection.application

import androidx.room.Room
import com.nunovalente.android.mypetagenda.util.Constants
import com.nunovalente.android.mypetagenda.application.MyApplication
import com.nunovalente.android.mypetagenda.data.Repository
import com.nunovalente.android.mypetagenda.data.local.PetDao
import com.nunovalente.android.mypetagenda.data.local.Database
import com.nunovalente.android.mypetagenda.data.local.PetLocalDataSource
import com.nunovalente.android.mypetagenda.data.local.ReminderDao
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
    fun petLocalDataSource(dao: PetDao) = PetLocalDataSource(dao)

    @Provides
    fun repository(petLocalDataSource: PetLocalDataSource) = Repository(petLocalDataSource)
}