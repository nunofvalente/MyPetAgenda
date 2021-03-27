package com.nunovalente.android.mypetagenda.ui.gallery

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.nunovalente.android.mypetagenda.util.CameraUseCase
import timber.log.Timber
import java.io.File
import java.lang.Exception
import javax.inject.Inject

class GalleryViewModel @Inject constructor(private val cameraUseCase: CameraUseCase) : ViewModel() {

    private var _images = MutableLiveData<List<Bitmap>>()
    val images: LiveData<List<Bitmap>>
        get() = _images

    private val _isDataLoading = MutableLiveData<Boolean>()
    val isDataLoading: LiveData<Boolean>
        get() = _isDataLoading.map { it != null }

    private val fileList = mutableListOf<Bitmap>()

    private val directory = cameraUseCase.getOutputDirectory()

   private fun retrieveFiles() {
        if (directory.exists()) {
            val files = directory.listFiles()
            if (files != null) {
                for (file: File in files) {
                    val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                    fileList.add(bitmap)
                }
            }
        }
        _images.value = fileList
    }

    fun refresh() {
        fileList.clear()
        retrieveFiles()
    }

    init {
        try {
            _isDataLoading.value = true
            retrieveFiles()
        } catch(e: Exception) {
            Timber.d("Error retrieving files ${e.localizedMessage}")
        } finally {
            _isDataLoading.value = false
        }
    }
}