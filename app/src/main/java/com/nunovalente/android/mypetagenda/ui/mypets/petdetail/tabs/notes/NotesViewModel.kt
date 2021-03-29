package com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.notes

import androidx.lifecycle.*
import com.nunovalente.android.mypetagenda.data.NoteRepository
import com.nunovalente.android.mypetagenda.models.Note
import com.nunovalente.android.mypetagenda.models.Pet
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class NotesViewModel @Inject constructor(private val repository: NoteRepository): ViewModel() {

    var noteList: LiveData<List<Note>>? = null

    private val _isDataLoading = MutableLiveData<Boolean>()
    val isDataLoading: LiveData<Boolean>
        get() = _isDataLoading.map { it != null }

    init {
        _isDataLoading.value = false
    }

    fun loadNotes(pet: Pet) {
        viewModelScope.launch {
            _isDataLoading.value = true
            try {
                noteList = repository.getAllNotes(pet.id)
            } catch (e: Exception) {
                Timber.d(e)
            } finally {
                _isDataLoading.value = false
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }
}