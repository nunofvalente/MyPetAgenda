package com.nunovalente.android.mypetagenda.data.local

import com.nunovalente.android.mypetagenda.data.Result
import com.nunovalente.android.mypetagenda.data.entities.DatabasePet
import com.nunovalente.android.mypetagenda.data.local.dao.PetDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.lang.RuntimeException
import javax.inject.Inject

class PetLocalDataSource @Inject constructor(private val dao: PetDao): PetDataSource {

    override suspend fun insertPet(databasePet: DatabasePet) {
        withContext(Dispatchers.IO) {
            dao.insertPet(databasePet)
        }
    }

     override suspend fun getPet(id: String): Result<DatabasePet> = withContext(Dispatchers.IO) {
        try {
            val pet = dao.getPet(id)
            if (pet != null) {
                return@withContext Result.Success(pet)
            } else {
                return@withContext Result.Error(Exception("Pet not found!"))
            }
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    override fun getAllPets() : Flow<List<DatabasePet>?> {
        try {
            return dao.getAllPets()
        } catch (e: Exception) {
            throw RuntimeException("Error loading pets!")
        }
    }

    override suspend fun deletePet(pet: DatabasePet) = withContext(Dispatchers.IO) {
        try {
            dao.deletePet(pet)
        } catch (e: Exception) {
            throw RuntimeException("Error deleting pet!")
        }
    }


}