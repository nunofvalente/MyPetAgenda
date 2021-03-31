package com.nunovalente.android.mypetagenda.models.maps

import com.squareup.moshi.Json

class Photo {
    @Json(name = "height")
    var height: Int? = null

    @Json(name = "html_attributions")
    var htmlAttributions: List<String>? = null

    @Json(name = "photo_reference")
    var photoReference: String? = null

    @Json(name = "width")
    var width: Int? = null
}