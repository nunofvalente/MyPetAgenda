package com.nunovalente.android.mypetagenda.data.entities

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.nunovalente.android.mypetagenda.models.Owner

data class DatabaseOwner(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "accountId")
    var accountId: String? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "email")
    var email: String? = null,

    @ColumnInfo(name = "password")
    var password: String? = null,

    @ColumnInfo(name = "imagePath")
    var imagePath: String? = null
)

fun DatabaseOwner.asDomainModel(): Owner {
    return Owner(
        this.id,
        this.accountId,
        this.name,
        this.email,
        this.password,
        this.imagePath
    )
}