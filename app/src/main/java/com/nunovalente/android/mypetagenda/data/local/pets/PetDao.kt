package com.nunovalente.android.mypetagenda.data.local.pets

import androidx.room.*
import com.nunovalente.android.mypetagenda.data.entities.DatabasePet
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {

    /**
     * Insert a new pet in the pet Table
     *
     * @param pet Pet to insert in the table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: DatabasePet?)

    /**
     * Update a pet from the pet table
     *
     * @param pet the pet we want to update
     */
    @Update(entity = DatabasePet::class)
    suspend fun updatePet(pet: DatabasePet?)

    /**
     * Delete a pet form pet table
     *
     * @param pet the pet we want to delete
     */
    @Delete
    suspend fun deletePet(pet: DatabasePet?)

    /**
     * Select a pet by id
     *
     * @param id the pet id
     * @return the pet with specified id
     */
    @Query("SELECT * FROM pet_table WHERE id = :id")
    suspend fun getPet(id: Int?): DatabasePet?

    /**
     * Select all pets
     *
     * @return List of all the pets related to the user account
     */
    @Query("SELECT * FROM pet_table")
    fun getAllPets(): Flow<List<DatabasePet>?>
}