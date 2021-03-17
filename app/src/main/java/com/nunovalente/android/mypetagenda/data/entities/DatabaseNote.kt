package com.nunovalente.android.mypetagenda.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nunovalente.android.mypetagenda.models.Note
import com.nunovalente.android.mypetagenda.models.Pet

@Entity(tableName = "note_table")
data class DatabaseNote(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "noteId")
    var noteId: Int,

    @ColumnInfo(name = "petId")
    var petId: Int,

    @ColumnInfo(name = "text")
    var text: String
)

fun List<DatabaseNote>.asDomainModel(): List<Note> {
    return map {
        Note(
            noteId = it.noteId,
            petId = it.petId,
            text = it.text
        )
    }
}

fun DatabaseNote.toDomainModel(): Note {
    return Note(
        this.noteId,
        this.petId,
        this.text
    )
}