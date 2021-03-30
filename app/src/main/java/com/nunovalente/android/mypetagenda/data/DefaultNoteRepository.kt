package com.nunovalente.android.mypetagenda.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.nunovalente.android.mypetagenda.data.entities.asDomainModel
import com.nunovalente.android.mypetagenda.data.entities.toDomainModel
import com.nunovalente.android.mypetagenda.data.local.notes.NoteDataSource
import com.nunovalente.android.mypetagenda.models.Note
import com.nunovalente.android.mypetagenda.models.toDatabaseModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultNoteRepository @Inject constructor(private val noteDataSource: NoteDataSource) :
    NoteRepository {

    override suspend fun addNote(note: Note) = withContext(Dispatchers.IO) {
        noteDataSource.addNote(note.toDatabaseModel())
    }

    override suspend fun updateNote(note: Note) {
        noteDataSource.updateNote(note.toDatabaseModel())
    }

    override suspend fun deleteNote(note: Note) {
        noteDataSource.deleteNote(note.toDatabaseModel())
    }

    override suspend fun deletePetNotes(petId: Int) {
        noteDataSource.deletePetNotes(petId)
    }

    override fun getAllNotes(petId: Int): LiveData<List<Note>> {
        return noteDataSource.getAllNotes(petId).asLiveData().map {
            it!!.asDomainModel()
        }
    }

    override suspend fun getNoteById(id: Int): Result<Note> {
        val result = noteDataSource.getNoteById(id)
        return if (result is Result.Success) {
            val databaseNote = result.data
            val pet = databaseNote!!.toDomainModel()
            Result.Success(pet)
        } else {
            Result.Error(Exception("Pet not found!"))
        }
    }

}