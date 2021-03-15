package com.nunovalente.android.mypetagenda.data.local

import com.nunovalente.android.mypetagenda.data.Result
import com.nunovalente.android.mypetagenda.data.entities.DatabaseNote
import com.nunovalente.android.mypetagenda.data.local.dao.NoteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.lang.RuntimeException
import javax.inject.Inject

class NoteLocalDataSource @Inject constructor(private val dao: NoteDao): NoteDataSource {

    override suspend fun addNote(note: DatabaseNote) {
        withContext(Dispatchers.IO) {
            dao.insertNote(note)
        }
    }

    override suspend fun updateNote(note: DatabaseNote) {
        try {
            return dao.updateNote(note)
        } catch (e: Exception) {
            throw RuntimeException("Error updating note!")
        }
    }

    override suspend fun deleteNote(note: DatabaseNote) {
        try {
            return dao.deleteNote(note)
        } catch (e: Exception) {
            throw RuntimeException("Error deleting note!")
        }
    }

    override fun getAllNotes(): Flow<List<DatabaseNote>?> {
        try {
            return dao.getAllNotes()
        } catch (e: Exception) {
            throw RuntimeException("Error loading pets!")
        }
    }

    override suspend fun getNoteById(id: Int): Result<DatabaseNote> = withContext(Dispatchers.IO) {
        try {
            val note = dao.getNote(id)
            if (note != null) {
                return@withContext Result.Success(note)
            } else {
                return@withContext Result.Error(Exception("Note not found!"))
            }
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }


}