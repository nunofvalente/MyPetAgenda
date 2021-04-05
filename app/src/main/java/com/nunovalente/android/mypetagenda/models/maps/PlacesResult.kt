package com.nunovalente.android.mypetagenda.models.maps

data class PlacesResult (
    var htmlAttributions: List<Any>? = null,
    var nextPageToken: String? = null,
    var results: List<Results>? = null,
    var status: String? = null
)