package com.nunovalente.android.mypetagenda.models

import android.os.Parcelable
import com.nunovalente.android.mypetagenda.data.entities.DatabasePet
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Pet(
    var id: Int = 0,
    var name: String = "",
    var birthday: String = "",
    var type: String = "",
    var breed: String = "",
    var weight: String = "",
    var imagePath: String = ""
) : Parcelable {
    constructor(name: String, birthday: String, type: String, breed: String, weight: String, imagePath: String) : this(0, name, birthday, type, breed, weight, imagePath)
}

fun Pet.toDatabasePet(): DatabasePet {
    return DatabasePet(
        this.id,
        this.name,
        this.birthday,
        this.type,
        this.breed,
        this.weight,
        this.imagePath
    )
}
