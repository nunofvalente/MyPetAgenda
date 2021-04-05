package com.nunovalente.android.mypetagenda.util

import android.location.Location
import java.util.*

object StringUtil {
    private val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    @JvmStatic
    fun calculateAge(text: String): String {
        val year = text.substring(6).toInt()
        val age = currentYear - year
        return age.toString()
    }

    @JvmStatic
    fun formatWeightString(text: String): String {
        return "$text Kg"
    }

    @JvmStatic
    fun getReminderTimeStringBuilder(hours: Int, minutes: Int): String {
        return String.format("%02d:%02d", hours, minutes)
    }

    @JvmStatic
    fun locationToString(location: Location): String {
        return "${location.latitude},${location.longitude}"
    }
}