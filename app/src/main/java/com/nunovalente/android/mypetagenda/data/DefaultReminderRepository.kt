package com.nunovalente.android.mypetagenda.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.nunovalente.android.mypetagenda.data.entities.toDomainModel
import com.nunovalente.android.mypetagenda.data.local.reminders.ReminderLocalDataSource
import com.nunovalente.android.mypetagenda.models.Reminder
import com.nunovalente.android.mypetagenda.models.toDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultReminderRepository @Inject constructor(private val dataSource: ReminderLocalDataSource): ReminderRepository {

    override suspend fun addReminder(reminder: Reminder) = withContext(Dispatchers.IO) {
        dataSource.insertReminder(reminder.toDatabaseModel())
    }

    override suspend fun updateReminder(reminder: Reminder) = withContext(Dispatchers.IO) {
        dataSource.updateReminder(reminder.toDatabaseModel())
    }

    override suspend fun deleteReminder(reminder: Reminder) = withContext(Dispatchers.IO) {
        dataSource.deleteReminder(reminder.toDatabaseModel())
    }

    override fun getAllReminder(reminderId: Int): LiveData<List<Reminder>> {
        return dataSource.getAllReminders().asLiveData().map {
            it!!.toDomainModel()
        }
    }

    override fun getPetReminders(petId: Int): LiveData<List<Reminder>> {
        return dataSource.getPetReminders(petId).asLiveData().map {
            it!!.toDomainModel()
        }
    }

    override suspend fun getReminderById(id: Int): Result<Reminder> {
        val result = dataSource.getReminder(id)
        return if (result is Result.Success) {
            val databaseReminder = result.data
            val reminder = databaseReminder.toDomainModel()
            Result.Success(reminder)
        } else {
            Result.Error(Exception("Pet not found!"))
        }
    }
}