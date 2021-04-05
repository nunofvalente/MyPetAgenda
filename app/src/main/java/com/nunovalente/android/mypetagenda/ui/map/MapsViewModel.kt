package com.nunovalente.android.mypetagenda.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nunovalente.android.mypetagenda.data.Result
import com.nunovalente.android.mypetagenda.models.maps.Results
import com.nunovalente.android.mypetagenda.networking.MapsApi
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class MapsViewModel @Inject constructor(private val mapsApi: MapsApi) : ViewModel() {

    private val _places = MutableLiveData<List<Results>?>()
    val places: LiveData<List<Results>?>
        get() = _places

    fun loadPlaces(location: String, radius: Int, searchParameter: String) {
        viewModelScope.launch {
            try {
                val placesResult = mapsApi.getPlacesByKeyword(location, radius, searchParameter)
                _places.value = placesResult.results
        } catch (e: Exception) {
            Timber.e("Error fetching places: ${e.message}")
        }
    }
}


}