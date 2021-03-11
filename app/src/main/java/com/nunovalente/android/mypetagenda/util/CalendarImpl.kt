package com.nunovalente.android.mypetagenda.util

import android.app.DatePickerDialog
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CalendarImpl @Inject constructor(
    private val activity: AppCompatActivity,
    private val calendar: Calendar
) {

    companion object {
        private const val DATE_FORMAT = "dd/MM/yyyy"
    }

    fun chooseDate(editText: EditText) {
        val date = DatePickerDialog.OnDateSetListener { view1, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(editText)
        }

        DatePickerDialog(
            activity,
            date,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateLabel(editText: EditText) {
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.UK)
        val date = sdf.format(calendar.time)
        editText.setText(date)
    }
}