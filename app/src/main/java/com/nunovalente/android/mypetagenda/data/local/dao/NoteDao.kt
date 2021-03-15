package com.nunovalente.android.mypetagenda.data.local.dao

import androidx.room.*
import com.nunovalente.android.mypetagenda.data.Result
import com.nunovalente.android.mypetagenda.data.entities.DatabaseNote
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    /**
     * Insert a new Note in the note Table
     *
     * @param note Note to insert in the table
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: DatabaseNote?)

    /**
     * Update a Note from the note table
     *
     * @param note the Note we want to update
     */
    @Update
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
    @Query("SELECT * FROM note_table")
    fun getAllNotes(): Flow<List<DatabaseNote>?>
}