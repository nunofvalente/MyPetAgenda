package com.nunovalente.android.mypetagenda.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nunovalente.android.mypetagenda.data.Repository
import javax.inject.Inject

class GalleryViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}