package com.nunovalente.android.mypetagenda.dependencyinjection.application

import androidx.room.Room
import com.nunovalente.android.mypetagenda.Constants
import com.nunovalente.android.mypetagenda.application.MyApplication
import com.nunovalente.android.mypetagenda.data.Repository
import com.nunovalente.android.mypetagenda.data.local.PetDao
import com.nunovalente.android.mypetagenda.data.local.PetDatabase
import com.nunovalente.android.mypetagenda.data.local.PetLocalDataSource
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @AppScope
    @Provides
    fun petDatabase(application: MyApplication): PetDatabase = Room.databaseBuilder(
        application.applicationContext,
        PetDatabase::class.java,
        Constants.PET_DATABASE_NAME
    ).build()

    @AppScope
    @Provides
    fun petDao(database: PetDatabase): PetDao = database.petDao()

    @AppScope
    @Provides
    fun petLocalDataSource(dao: PetDao) = PetLocalDataSource(dao)

    @AppScope
    @Provides
    fun repository(petLocalDataSource: PetLocalDataSource) = Repository(petLocalDataSource)
}