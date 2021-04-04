package com.nunovalente.android.mypetagenda.networking

import com.google.android.gms.maps.model.LatLng
import com.nunovalente.android.mypetagenda.models.maps.Results
import retrofit2.http.GET
import retrofit2.http.Query

interface MapsApi {

    @GET("json")
    suspend fun getPlacesByKeyword(
        @Query("location") location: LatLng,
        @Query("radius") radius: Int,
        @Query("keyword") keyword: String
    ): Results
}