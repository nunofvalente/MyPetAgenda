package com.nunovalente.android.mypetagenda.models.maps

import com.squareup.moshi.Json

class OpeningHours {
    @Json(name = "open_now")
    var openNow: Boolean? = null
}