package com.nunovalente.android.mypetagenda.networking

import com.nunovalente.android.mypetagenda.models.maps.PlacesResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MapsApi {

    @GET("json")
    suspend fun getPlacesByKeyword(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("keyword") keyword: String
    ): PlacesResult
}