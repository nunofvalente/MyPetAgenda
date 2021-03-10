package com.nunovalente.android.mypetagenda.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nunovalente.android.mypetagenda.models.Pet

@Entity(tableName = "pet_table")
data class DatabasePet(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "birthday")
    val birthday: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "breed")
    val breed: String,

    @ColumnInfo(name = "weight")
    val weight: String,

    @ColumnInfo(name = "imagePath")
    val imagePath: String
)

fun List<DatabasePet>.asDomainModel(): List<Pet> {
    return map {
        Pet(
            id = it.id,
            name = it.name,
            birthday = it.birthday,
            type = it.type,
            breed = it.breed,
            weight = it.weight,
            imagePath = it.imagePath
        )
    }
}

fun DatabasePet.toDomainModel(): Pet {
    return Pet(this.id,
        this.name,
        this.birthday,
        this.type,
        this.breed,
        this.weight,
        this.imagePath
    )
}

