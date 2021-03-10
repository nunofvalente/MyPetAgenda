package com.nunovalente.android.mypetagenda.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.nunovalente.android.mypetagenda.data.entities.DatabasePet
import com.nunovalente.android.mypetagenda.data.entities.asDomainModel
import com.nunovalente.android.mypetagenda.data.entities.toDomainModel
import com.nunovalente.android.mypetagenda.data.local.PetLocalDataSource
import com.nunovalente.android.mypetagenda.models.Pet
import com.nunovalente.android.mypetagenda.models.toDatabasePet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: PetLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun insertPet(pet: Pet) = withContext(ioDispatcher) {
        localDataSource.insertPet(pet.toDatabasePet())
    }

    suspend fun getPet(id: String): Result<Pet> {
        val result = localDataSource.getPet(id)
        return if (result is Result.Success) {
            val databasePet = result.data
            val pet = databasePet.toDomainModel()
            Result.Success(pet)
        } else {
            Result.Error(Exception("Pet not found!"))
        }
    }

    fun getAllPets(): LiveData<List<Pet>?> {
        return localDataSource.getAllPets().asLiveData().map {
            it?.asDomainModel()
        }
    }
}