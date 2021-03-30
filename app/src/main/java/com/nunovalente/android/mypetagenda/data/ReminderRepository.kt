package com.nunovalente.android.mypetagenda.data

import androidx.lifecycle.LiveData
import com.nunovalente.android.mypetagenda.models.Reminder

interface ReminderRepository {
    suspend fun addReminder(reminder: Reminder)
    suspend fun updateReminder(reminder: Reminder)
    suspend fun deleteReminder(reminder: Reminder)
    fun getAllReminders(): LiveData<List<Reminder>>
    fun getPetReminders(petId: Int): LiveData<List<Reminder>>
    suspend fun deletePetReminders(petId: Int)
    suspend fun getReminderById(id: Int): Result<Reminder>
}