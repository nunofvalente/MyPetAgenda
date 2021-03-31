package com.nunovalente.android.mypetagenda.models.maps

import com.squareup.moshi.Json

class Geometry {
    @Json(name = "location")
    var location: Location? = null

    @Json(name = "viewport")
    var viewport: Viewport? = null
}