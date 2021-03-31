package com.nunovalente.android.mypetagenda.models.maps

import com.squareup.moshi.Json

class Results {
    @Json(name = "business_status")
    var businessStatus: String? = null

    @Json(name = "geometry")
    var geometry: Geometry? = null

    @Json(name = "icon")
    var icon: String? = null

    @Json(name = "name")
    var name: String? = null

    @Json(name = "opening_hours")
    var openingHours: OpeningHours? = null

    @Json(name = "photos")
    var photos: List<Photo>? = null

    @Json(name = "place_id")
    var placeId: String? = null

    @Json(name = "plus_code")
    var plusCode: PlusCode? = null

    @Json(name = "price_level")
    var priceLevel: Int? = null

    @Json(name = "rating")
    var rating: Int? = null

    @Json(name = "reference")
    var reference: String? = null

    @Json(name = "scope")
    var scope: String? = null

    @Json(name = "types")
    var types: List<String>? = null

    @Json(name = "user_ratings_total")
    var userRatingsTotal: Int? = null

    @Json(name = "vicinity")
    var vicinity: String? = null
}