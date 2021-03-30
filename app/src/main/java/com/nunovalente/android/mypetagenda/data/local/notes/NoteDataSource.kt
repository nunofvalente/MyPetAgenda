package com.nunovalente.android.mypetagenda.data.local.notes

import com.nunovalente.android.mypetagenda.data.Result
import com.nunovalente.android.mypetagenda.data.entities.DatabaseNote
import kotlinx.coroutines.flow.Flow

interface NoteDataSource {
    suspend fun addNote(note: DatabaseNote)
    suspend fun updateNote(note: DatabaseNote)
    suspend fun deleteNote(note: DatabaseNote)
    suspend fun deletePetNotes(petId: Int)
    fun getAllNotes(petId: Int): Flow<List<DatabaseNote>?>
    suspend fun getNoteById(id: Int): Result<DatabaseNote?>
}