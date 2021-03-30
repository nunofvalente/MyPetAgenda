package com.nunovalente.android.mypetagenda.data.local.notes

import androidx.room.*
import com.nunovalente.android.mypetagenda.data.entities.DatabaseNote
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    /**
     * Insert a new Note in the note Table
     *
     * @param note Note to insert in the table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: DatabaseNote?)

    /**
     * Update a Note from the note table
     *
     * @param note the Note we want to update
     */
    @Update(entity = DatabaseNote::class)
    suspend fun updateNote(note: DatabaseNote?)

    /**
     * Delete a Note form note table
     *
     * @param note the Note we want to delete
     */
    @Delete
    suspend fun deleteNote(note: DatabaseNote?)

    /**
     * Select a Note by id
     *
     * @param id the Note id
     * @return the Note with specified id
     */
    @Query("SELECT * FROM note_table WHERE noteId = :id")
    suspend fun getNote(id: Int?): DatabaseNote?

    /**
     * Select all Note
     *
     * @return List of all the Notes related to the user account
     */
    @Query("SELECT * FROM note_table WHERE petId = :petId")
    fun getAllNotes(petId: Int): Flow<List<DatabaseNote>?>

    /**
     * Delete all Note associated with specific pet
     *
     */
    @Query("DELETE FROM note_table WHERE petId = :petId")
    suspend fun deletePetNotes(petId: Int)
}