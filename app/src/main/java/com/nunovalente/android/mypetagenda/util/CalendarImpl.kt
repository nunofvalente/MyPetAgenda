package com.nunovalente.android.mypetagenda.util

import android.app.DatePickerDialog
import android.content.Context
import android.widget.EditText
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CalendarImpl @Inject constructor(private val calendar: Calendar) {

    companion object {
        private const val DATE_FORMAT = "dd/MM/yyyy"

        @JvmStatic
        fun retrieveYear(): String {
            val calendar = Calendar.getInstance()
            val sdf = SimpleDateFormat(DATE_FORMAT, Locale.UK)
            return sdf.format(calendar.time)
        }
    }

    fun chooseDate(context: Context, editText: EditText) {
        val date = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(editText)
        }

        DatePickerDialog(
            context,
            date,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateLabel(editText: EditText) {
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.UK)
        val date = sdf.format(calendar.time)
        val chosenDate = sdf.format(calendar.time)

        if(date > chosenDate) {
            Toast.makeText(editText.context, "Please choose a valid date!", Toast.LENGTH_SHORT).show()
        } else {
            editText.setText(date)
        }
    }

    fun chooseDateReminder(context: Context, editText: EditText) {
        val date = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelReminder(editText)
        }

        DatePickerDialog(
            context,
            date,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateLabelReminder(editText: EditText) {
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.UK)
        val currentDate = sdf.format(System.currentTimeMillis())
        val chosenDate = sdf.format(calendar.time)

       if(chosenDate < currentDate) {
           Toast.makeText(editText.context, "Please enter a valid date!", Toast.LENGTH_SHORT).show()
       }else {
           editText.setText(chosenDate)
       }
    }
}