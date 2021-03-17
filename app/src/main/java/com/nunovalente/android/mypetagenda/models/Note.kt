package com.nunovalente.android.mypetagenda.models

import android.os.Parcelable
import com.nunovalente.android.mypetagenda.data.entities.DatabaseNote
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Note(
    var noteId: Int,
    var petId: Int,
    var text: String
) : Parcelable {
    constructor(petId: Int, text: String) : this(0, petId, text)
}

fun Note.toDatabaseModel(): DatabaseNote {
    return DatabaseNote(
        this.noteId,
        this.petId,
        this.text
    )
}