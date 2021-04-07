package com.nunovalente.android.mypetagenda.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nunovalente.android.mypetagenda.data.PetRepository
import com.nunovalente.android.mypetagenda.data.Result
import com.nunovalente.android.mypetagenda.data.entities.DatabasePet
import com.nunovalente.android.mypetagenda.data.entities.asDomainModel
import com.nunovalente.android.mypetagenda.data.entities.toDomainModel
import com.nunovalente.android.mypetagenda.models.Pet
import com.nunovalente.android.mypetagenda.models.toDatabasePet
import kotlinx.coroutines.runBlocking

class FakeRepository : PetRepository {

    private var petServiceData: LinkedHashMap<Int, DatabasePet> = LinkedHashMap()

    private val observableTasks = MutableLiveData<List<Pet>>()

    override suspend fun insertPet(pet: Pet) {
        petServiceData[pet.id] = pet.toDatabasePet()
    }

    override suspend fun getPet(id: Int): Result<Pet> {
        val result = petServiceData[id]
        return if (result != null) {
            Result.Success(result.toDomainModel())
        } else {
            Result.Error(Exception("Pet not found!"))
        }
    }

    override fun getAllPets(): LiveData<List<Pet>?> {
        observableTasks.value = petServiceData.values.toList().asDomainModel()
        return observableTasks
    }

    override suspend fun deletePet(pet: Pet) {
        petServiceData.remove(pet.id)
    }

    override suspend fun updatePet(pet: Pet) {
        petServiceData[pet.id] = pet.toDatabasePet()
    }
}