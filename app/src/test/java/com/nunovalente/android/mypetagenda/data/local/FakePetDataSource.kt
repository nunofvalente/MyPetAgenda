package com.nunovalente.android.mypetagenda.data.local

import com.nunovalente.android.mypetagenda.data.Result
import com.nunovalente.android.mypetagenda.data.entities.DatabasePet
import kotlinx.coroutines.flow.*

class FakePetDataSource(private var petsList: MutableList<DatabasePet>?) : PetDataSource {

    override suspend fun insertPet(databasePet: DatabasePet) {
        petsList?.add(databasePet)
    }

    override suspend fun getPet(id: String): Result<DatabasePet> {
        var selectedPet: DatabasePet? = null
        for (pet in petsList!!) {
            if (pet.id == id) {
                selectedPet = pet
            }
        }
        if (selectedPet != null) {
            return Result.Success(selectedPet)
        }
        return Result.Error(Exception("Could not find pet with $id"))
    }


    override fun getAllPets(): Flow<List<DatabasePet>?> {
        val list = petsList?.toList()
        return flow { emit(list) }
    }

    override suspend fun deletePet(pet: DatabasePet) {
        petsList?.remove(pet)
    }
}