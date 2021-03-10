package com.nunovalente.android.mypetagenda.ui.mypets

import androidx.lifecycle.*
import com.nunovalente.android.mypetagenda.data.Repository
import com.nunovalente.android.mypetagenda.models.Pet
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddPetViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _pet = MutableLiveData<Pet>()
    val pet: LiveData<Pet>
        get() = _pet

    private val _navigate = MutableLiveData<Boolean>()
    val navigate: LiveData<Boolean>
        get() = _navigate

    init {
        _navigate.value = false
        _pet.value = Pet("","","","","","")
    }


    fun savePetToDb() {
        viewModelScope.launch {
            pet.value?.let { repository.insertPet(it) }
            navigateToMyPets()
        }
    }

    fun navigateToMyPets() {
        _navigate.value = true
    }

    fun doneNavigating() {
        _navigate.value = false
    }
}

@Suppress("UNCHECKED_CAST")
class AddPetViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddPetViewModel(repository) as T
    }
}