package com.nunovalente.android.mypetagenda.data

import androidx.lifecycle.LiveData
import com.nunovalente.android.mypetagenda.models.Note

interface NoteRepository {
    suspend fun addNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    fun getAllNotes(petId: Int): LiveData<List<Note>>
    suspend fun getNoteById(id: Int): Result<Note>
}