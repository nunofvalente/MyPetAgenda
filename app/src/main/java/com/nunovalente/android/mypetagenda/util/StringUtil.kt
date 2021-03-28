package com.nunovalente.android.mypetagenda.util

import java.util.*

object StringUtil {
    private val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    @JvmStatic
    fun calculateAge(text: String): String {
        val year = text.substring(6, 9).toInt()
        val age = currentYear - year
        return age.toString()
    }

    @JvmStatic
    fun formatWeightString(text: String): String {
        return "$text Kg"
    }
}