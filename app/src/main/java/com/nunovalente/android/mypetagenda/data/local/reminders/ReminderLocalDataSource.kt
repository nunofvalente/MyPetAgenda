package com.nunovalente.android.mypetagenda.data.local.reminders

import com.nunovalente.android.mypetagenda.data.Result
import com.nunovalente.android.mypetagenda.data.entities.DatabaseReminder
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderLocalDataSource @Inject constructor(private val dao: ReminderDao): ReminderDataSource {

    override suspend fun insertReminder(databaseReminder: DatabaseReminder) {
        TODO("Not yet implemented")
    }

    override suspend fun getReminder(id: Int): Result<DatabaseReminder> {
        TODO("Not yet implemented")
    }

    override fun getAllReminders(): Flow<List<DatabaseReminder>?> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReminder(reminder: DatabaseReminder) {
        TODO("Not yet implemented")
    }

    override suspend fun updateReminder(reminder: DatabaseReminder) {
        TODO("Not yet implemented")
    }
}