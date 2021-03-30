package com.nunovalente.android.mypetagenda.ui.mypets.pets

import androidx.lifecycle.*
import com.nunovalente.android.mypetagenda.data.NoteRepository
import com.nunovalente.android.mypetagenda.data.PetRepository
import com.nunovalente.android.mypetagenda.data.ReminderRepository
import com.nunovalente.android.mypetagenda.models.Pet
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class MyPetsViewModel @Inject constructor(
    private val petRepository: PetRepository,
    private val reminderRepository: ReminderRepository,
    private val noteRepository: NoteRepository
) : ViewModel() {

    lateinit var petList: LiveData<List<Pet>?>

    private val _isDataLoading = MutableLiveData<Boolean>()
    val isDataLoading: LiveData<Boolean>
        get() = _isDataLoading.map { it != null }

    init {
        loadPets()
    }

    fun loadPets() {
        viewModelScope.launch {
            _isDataLoading.value = true
            try {
                petList = petRepository.getAllPets()
            } catch (e: Exception) {
                Timber.d(e)
            } finally {
                _isDataLoading.value = false
            }
        }
    }

    fun deletePet(pet: Pet) {
        viewModelScope.launch {
            petRepository.deletePet(pet)
            reminderRepository.deletePetReminders(pet.id)
            noteRepository.deletePetNotes(pet.id)
        }
    }
}