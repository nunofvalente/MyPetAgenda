package com.nunovalente.android.mypetagenda.data

import androidx.lifecycle.LiveData
import com.nunovalente.android.mypetagenda.data.entities.DatabasePet
import com.nunovalente.android.mypetagenda.models.Pet

interface PetRepository {

    suspend fun insertPet(pet: Pet)
    suspend fun getPet(id: Int): Result<Pet>
    fun getAllPets(): LiveData<List<Pet>?>
    suspend fun deletePet(pet: Pet)
    suspend fun updatePet(pet: Pet)
}