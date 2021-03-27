package com.nunovalente.android.mypetagenda.data

import androidx.lifecycle.LiveData
import com.nunovalente.android.mypetagenda.models.Reminder

interface ReminderRepository {
    suspend fun addReminder(reminder: Reminder)
    suspend fun updateReminder(reminder: Reminder)
    suspend fun deleteReminder(reminder: Reminder)
    fun getAllReminder(reminderId: Int): LiveData<List<Reminder>>
    suspend fun getReminderById(id: Int): Result<Reminder>
}