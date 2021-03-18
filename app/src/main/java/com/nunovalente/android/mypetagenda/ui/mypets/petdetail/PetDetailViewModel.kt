package com.nunovalente.android.mypetagenda.ui.mypets.petdetail

import androidx.lifecycle.*
import com.nunovalente.android.mypetagenda.data.NoteRepository
import com.nunovalente.android.mypetagenda.models.Note
import com.nunovalente.android.mypetagenda.models.Pet
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class PetDetailViewModel: ViewModel() {
    private var pet = MutableLiveData<Pet>()

    fun setPetValue(petRetrieved: Pet) {
        pet.value = petRetrieved
    }

    fun getPetRetrieved(): Pet? {
        return pet.value
    }

}