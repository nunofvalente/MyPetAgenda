package com.nunovalente.android.mypetagenda.models.maps

import com.squareup.moshi.Json

class PlusCode {
    @Json(name = "compound_code")
    var compoundCode: String? = null

    @Json(name = "global_code")
    var globalCode: String? = null
}