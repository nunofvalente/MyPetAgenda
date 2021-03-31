package com.nunovalente.android.mypetagenda.models.maps

import com.squareup.moshi.Json

class Viewport {
    @Json(name = "northeast")
    var northeast: Northeast? = null

    @Json(name = "southwest")
    var southwest: Southwest? = null
}