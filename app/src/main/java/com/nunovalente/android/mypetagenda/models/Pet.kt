package com.nunovalente.android.mypetagenda.models

import com.nunovalente.android.mypetagenda.data.entities.DatabasePet
import java.util.*

data class Pet(
    var id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var birthday: String = "",
    var type: String = "",
    var breed: String = "",
    var weight: String = "",
    var imagePath: String = ""
) {
    constructor(name: String, birthday: String, type: String, breed: String, weight: String, imagePath: String) : this(UUID.randomUUID().toString(), name, birthday, type, breed, weight, imagePath)
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
