package com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.reminders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nunovalente.android.mypetagenda.data.DefaultReminderRepository
import com.nunovalente.android.mypetagenda.models.Reminder
import javax.inject.Inject
import kotlin.random.Random

class AddReminderViewModel @Inject constructor(private val repository: DefaultReminderRepository) :
    ViewModel() {

    private val _reminder = MutableLiveData<Reminder>()
    val reminder: LiveData<Reminder>
        get() = _reminder

    init {

        //Initializes the Reminder so it is not null at initialization
        _reminder.value = Reminder(
            Random.nextInt(Integer.MAX_VALUE),
            0,
            "",
            "",
            0,
            0,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            "")
    }

    fun setNotRecurring() {
        _reminder.value?.isRecurring = false
    }

    fun setRecurring() {
        _reminder.value?.isRecurring = true
    }


    /**
     * Sets Reminder information from User input
     */
    fun setInfo(petId: Int, petName: String, minutes: Int, hour: Int, date: String) {
        val value = _reminder.value
        value?.apply {
            this.petId = petId
            this.petName = petName
            this.minutes = minutes
            this.hour = hour

            if(!value.isRecurring) {
                this.date = date
            } else {
                this.date = ""
            }
        }
    }

    fun startReminder() {
        _reminder.value?.apply {
            isStarted = true
        }
    }
}

@Suppress("UNCHECKED_CAST")
class AddReminderViewModelFactory(private val repository: DefaultReminderRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddReminderViewModel(repository) as T
    }
}