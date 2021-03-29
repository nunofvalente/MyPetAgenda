package com.nunovalente.android.mypetagenda.data.local.reminders

import com.nunovalente.android.mypetagenda.data.Result
import com.nunovalente.android.mypetagenda.data.entities.DatabaseReminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.lang.RuntimeException
import javax.inject.Inject

class ReminderLocalDataSource @Inject constructor(private val dao: ReminderDao): ReminderDataSource {

    override suspend fun insertReminder(databaseReminder: DatabaseReminder) {
        withContext(Dispatchers.IO) {
            dao.insertReminder(databaseReminder)
        }
    }

    override suspend fun getReminder(id: Int): Result<DatabaseReminder> = withContext(Dispatchers.IO) {
        try {
            val reminder = dao.getReminder(id)
            if (reminder != null) {
                return@withContext Result.Success(reminder)
            } else {
                return@withContext Result.Error(Exception("Reminder not found!"))
            }
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    override fun getAllReminders(): Flow<List<DatabaseReminder>?> {
        try {
            return dao.getAllReminders()
        } catch (e: Exception) {
            throw RuntimeException("Error loading reminders!")
        }
    }

    override fun getPetReminders(petId: Int): Flow<List<DatabaseReminder>?> {
        try {
            return dao.getPetReminders(petId)
        } catch (e: Exception) {
            throw RuntimeException("Error loading reminders!")
        }
    }

    override suspend fun deleteReminder(reminder: DatabaseReminder) = withContext(Dispatchers.IO) {
        try {
            dao.deleteReminder(reminder)
        } catch (e: Exception) {
            throw RuntimeException("Error deleting reminder!")
        }
    }

    override suspend fun updateReminder(reminder: DatabaseReminder) = withContext(Dispatchers.IO) {
        try {
            dao.updateReminder(reminder)
        } catch (e: Exception) {
            throw RuntimeException("Error updating reminder!")
        }
    }
}