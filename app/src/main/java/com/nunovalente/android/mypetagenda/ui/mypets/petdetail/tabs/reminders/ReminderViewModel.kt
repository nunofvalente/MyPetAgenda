package com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.reminders

import androidx.lifecycle.*
import com.nunovalente.android.mypetagenda.data.DefaultPetRepository
import com.nunovalente.android.mypetagenda.data.ReminderRepository
import com.nunovalente.android.mypetagenda.models.Note
import com.nunovalente.android.mypetagenda.models.Pet
import com.nunovalente.android.mypetagenda.models.Reminder
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class ReminderViewModel @Inject constructor(private val repository: ReminderRepository): ViewModel() {

    var reminderList: LiveData<List<Reminder>>? = null

    private val _isDataLoading = MutableLiveData<Boolean>()
    val isDataLoading: LiveData<Boolean>
        get() = _isDataLoading.map { it != null }

    init {
        _isDataLoading.value = false
    }

    fun loadReminders(petId: Int) {
        viewModelScope.launch {
            _isDataLoading.value = true
            try {
                reminderList = repository.getPetReminders(petId)
            } catch (e: Exception) {
                Timber.d(e)
            } finally {
                _isDataLoading.value = false
            }
        }
    }

    fun cancelReminder(reminder: Reminder) {
        reminder.isStarted = false

    }

    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch {
            repository.deleteReminder(reminder)
        }
    }

    fun updateReminder(reminder: Reminder) {
        viewModelScope.launch {
            repository.updateReminder(reminder)
        }
    }
}