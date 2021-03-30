package com.nunovalente.android.mypetagenda.data.local.reminders

import androidx.room.*
import com.nunovalente.android.mypetagenda.data.entities.DatabaseReminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    /**
     * Insert a new reminder in the reminder Table
     *
     * @param reminder Reminder to insert in the table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: DatabaseReminder?)

    /**
     * Update a reminder from the reminder table
     *
     * @param reminder the reminder we want to update
     */
    @Update(entity = DatabaseReminder::class)
    suspend fun updateReminder(reminder: DatabaseReminder?)

    /**
     * Delete a reminder from reminder table
     *
     * @param reminder the reminder we want to delete
     */
    @Delete
    suspend fun deleteReminder(reminder: DatabaseReminder?)

    /**
     * Select a reminder by id
     *
     * @param id the reminder id
     * @return the reminder with specified id
     */
    @Query("SELECT * FROM reminder_table WHERE id = :id")
    suspend fun getReminder(id: Int?): DatabaseReminder?

    /**
     * Select all reminders
     *
     * @return List of all the reminders related to the user account
     */
    @Query("SELECT * FROM reminder_table")
    fun getAllReminders(): Flow<List<DatabaseReminder>?>

    /**
     * Retrieve all reminders associated with a specific pet
     *
     * @return List of all the reminders related to the pet
     */
    @Query("SELECT * FROM reminder_table WHERE petId = :petId")
    fun getPetReminders(petId: Int): Flow<List<DatabaseReminder>?>

    /**
     * Delete all reminders associated with a specific pet
     *
     */
    @Query("DELETE FROM reminder_table WHERE petId = :petId")
    suspend fun deletePetReminders(petId: Int)
}