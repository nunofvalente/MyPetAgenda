package com.nunovalente.android.mypetagenda.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nunovalente.android.mypetagenda.models.Pet
import com.nunovalente.android.mypetagenda.models.toDatabasePet

@Entity(tableName = "pet_table")
data class DatabasePet(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

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
    val imagePath: String,

    @ColumnInfo(name = "bath")
    val bath: String = "",

    @ColumnInfo(name = "vaccination")
    val vaccination: String = "",

    @ColumnInfo(name = "wormTreatment")
    val wormTreatment: String = "",

    @ColumnInfo(name = "veterinarian")
    val veterinarian: String = "",

    @ColumnInfo(name = "fleaTreatment")
    val fleaTreatment: String = ""
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
            imagePath = it.imagePath,
            bath = it.bath,
            vaccination = it.vaccination,
            wormTreatment = it.wormTreatment,
            veterinarian = it.veterinarian,
            fleaTreatment = it.fleaTreatment
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
        this.imagePath,
        this.bath,
        this.vaccination,
        this.wormTreatment,
        this.veterinarian,
        this.fleaTreatment
    )
}

