package com.nunovalente.android.mypetagenda.data.local

import com.nunovalente.android.mypetagenda.data.Result
import com.nunovalente.android.mypetagenda.data.entities.DatabasePet
import kotlinx.coroutines.flow.Flow

interface PetDataSource {

    suspend fun insertPet(databasePet: DatabasePet)
    suspend fun getPet(id: String): Result<DatabasePet>
    fun getAllPets(): Flow<List<DatabasePet>?>
    suspend fun deletePet(pet: DatabasePet)
}