package com.nunovalente.android.mypetagenda.data.local.reminders

import com.nunovalente.android.mypetagenda.data.Result
import com.nunovalente.android.mypetagenda.data.entities.DatabaseReminder
import kotlinx.coroutines.flow.Flow

interface ReminderDataSource {
    suspend fun insertReminder(databaseReminder: DatabaseReminder)
    suspend fun getReminder(id: Int): Result<DatabaseReminder>
    fun getAllReminders(): Flow<List<DatabaseReminder>?>
    suspend fun deleteReminder(reminder: DatabaseReminder)
    suspend fun updateReminder(reminder: DatabaseReminder)
}