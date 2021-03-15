package com.nunovalente.android.mypetagenda.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class DatabaseNote(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "noteId")
    val noteId: Int,

    @ColumnInfo(name = "text")
    val text: String
)