package com.nunovalente.android.mypetagenda.models

data class Reminder (
    val id: Int,
    val petId: String,
    val petName: String,
    val title: String,
    val hour: String,
    val minutes: String,
    val isRecurring: Boolean
    )