package com.nunovalente.android.mypetagenda.models.maps

import com.squareup.moshi.Json

class PlacesResult {
    @Json(name = "html_attributions")
    var htmlAttributions: List<Any>? = null

    @Json(name = "next_page_token")
    var nextPageToken: String? = null

    @Json(name = "results")
    var results: List<Results>? = null

    @Json(name = "status")
    var status: String? = null
}