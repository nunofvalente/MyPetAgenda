package com.nunovalente.android.mypetagenda.models.maps

data class Results (
    var businessStatus: String? = null,
    var geometry: Geometry? = null,
    var icon: String? = null,
    var name: String? = null,
    var openingHours: OpeningHours? = null,
    var photos: List<Photo>? = null,
    var placeId: String? = null,
    var plusCode: PlusCode? = null,
    var priceLevel: Int? = null,
    var rating: Double? = null,
    var reference: String? = null,
    var scope: String? = null,
    var types: List<String>? = null,
    var userRatingsTotal: Int? = null,
    var vicinity: String? = null
)