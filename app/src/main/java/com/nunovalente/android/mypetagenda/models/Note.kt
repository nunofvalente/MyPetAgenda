package com.nunovalente.android.mypetagenda.models

import com.nunovalente.android.mypetagenda.data.entities.DatabaseNote

data class Note(
    var text: String
) {
    var noteId: Int = 0
}

fun Note.toDatabaseModel(): DatabaseNote {
    return DatabaseNote(
        this.text
    )
}