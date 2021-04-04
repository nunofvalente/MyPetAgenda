package com.nunovalente.android.mypetagenda.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.nunovalente.android.mypetagenda.models.maps.PlacesResult
import com.nunovalente.android.mypetagenda.networking.MapsApi
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class MapsViewModel @Inject constructor(private val mapsApi: MapsApi): ViewModel() {

    private val _places = MutableLiveData<PlacesResult>()
    val places: LiveData<PlacesResult>
        get() = _places

    fun loadPlaces(location: LatLng, radius: Int,  searchParameter: String) {
        viewModelScope.launch {
            try {
                _places.value = mapsApi.getPlacesByKeyword(location, radius, searchParameter)
            } catch (e: Exception) {
                Timber.e("Error fetching places: ${e.message}")
            }

        }
    }
}