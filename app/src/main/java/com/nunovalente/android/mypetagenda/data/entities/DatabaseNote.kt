package com.nunovalente.android.mypetagenda.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nunovalente.android.mypetagenda.models.Note
import com.nunovalente.android.mypetagenda.models.Pet

@Entity(tableName = "note_table")
data class DatabaseNote(
    @ColumnInfo(name = "text")
    var text: String
) {
    @PrimaryKey(autoGenerate = true)
    var noteId: Int = 0
}

fun List<DatabaseNote>.asDomainModel(): List<Note> {
    return map {
        Note(
            text = it.text
        )
    }
}

fun DatabaseNote.toDomainModel(): Note {
    return Note(
        this.text
    )
}