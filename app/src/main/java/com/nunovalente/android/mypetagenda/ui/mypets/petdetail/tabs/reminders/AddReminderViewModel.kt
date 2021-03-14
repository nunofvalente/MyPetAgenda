package com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.reminders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.nunovalente.android.mypetagenda.data.Repository
import javax.inject.Inject

class AddReminderViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _isRecurring = MutableLiveData<Boolean>()
    val isRecurring: LiveData<Boolean>
        get() = _isRecurring.map { it != null }

    init {
        _isRecurring.value = false
    }

    fun setRecurring() {
       _isRecurring.value = true
    }

    fun setNotRecurring() {
        _isRecurring.value = false
    }
}