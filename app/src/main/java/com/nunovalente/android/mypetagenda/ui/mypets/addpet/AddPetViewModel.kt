package com.nunovalente.android.mypetagenda.ui.mypets.addpet

import androidx.lifecycle.*
import com.nunovalente.android.mypetagenda.data.DefaultPetRepository
import com.nunovalente.android.mypetagenda.models.Pet
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddPetViewModel @Inject constructor(private val defaultPetRepository: DefaultPetRepository): ViewModel() {

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
            pet.value?.let { defaultPetRepository.insertPet(it) }
            navigateToMyPets()
        }
    }

    private fun navigateToMyPets() {
        _navigate.value = true
    }

    fun doneNavigating() {
        _navigate.value = false
    }
}

@Suppress("UNCHECKED_CAST")
class AddPetViewModelFactory(private val defaultPetRepository: DefaultPetRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddPetViewModel(defaultPetRepository) as T
    }
}