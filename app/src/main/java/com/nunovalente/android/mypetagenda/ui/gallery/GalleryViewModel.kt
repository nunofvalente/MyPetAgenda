package com.nunovalente.android.mypetagenda.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.nunovalente.android.mypetagenda.data.Repository
import javax.inject.Inject

class GalleryViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _isDataLoading = MutableLiveData<Boolean>()
    val isDataLoading: LiveData<Boolean>
        get() = _isDataLoading.map { it != null }

}