package com.nunovalente.android.mypetagenda.data

import androidx.lifecycle.LiveData
import com.nunovalente.android.mypetagenda.models.Note
import com.nunovalente.android.mypetagenda.models.Pet

interface NoteRepository {
    suspend fun addNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun deletePetNotes(petId: Int)
    fun getAllNotes(petId: Int): LiveData<List<Note>>
    suspend fun getNoteById(id: Int): Result<Note>
}