package com.nunovalente.android.mypetagenda.ui.mypets.pets

import androidx.lifecycle.*
import com.nunovalente.android.mypetagenda.data.Repository
import com.nunovalente.android.mypetagenda.models.Pet
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class MyPetsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    lateinit var petList: LiveData<List<Pet>?>

    private val _isDataLoading = MutableLiveData<Boolean>()
    val isDataLoading: LiveData<Boolean>
        get() = _isDataLoading.map { it != null }

    private val _navigate = MutableLiveData<Boolean>()
    val navigate: LiveData<Boolean>
        get() = _navigate

    fun navigateToAddPet() {
        _navigate.value = true
    }

    fun doneNavigating() {
        _navigate.value = false
    }

    init {
        _navigate.value = false

        viewModelScope.launch {
            _isDataLoading.value = true
            try {
                petList = repository.getAllPets()
            } catch (e: Exception) {
                Timber.d(e)
            } finally {
                _isDataLoading.value = false
            }
        }
    }
}