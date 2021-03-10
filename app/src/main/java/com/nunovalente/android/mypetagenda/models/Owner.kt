package com.nunovalente.android.mypetagenda.models

import com.nunovalente.android.mypetagenda.data.entities.DatabaseOwner

data class Owner(
    var id: String,
    val accountId: String? = null,
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val imagePath: String? = null
)

fun Owner.asDatabaseModel(): DatabaseOwner {
    return DatabaseOwner(
        this.id,
        this.accountId,
        this.name,
        this.email,
        this.password,
        this.imagePath
    )
}