package com.nunovalente.android.mypetagenda.models.maps

import com.squareup.moshi.Json

class Northeast {
    @Json(name = "lat")
    var lat: Double? = null

    @Json(name = "lng")
    var lng: Double? = null
}