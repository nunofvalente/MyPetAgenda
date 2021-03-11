package com.nunovalente.android.mypetagenda.dependencyinjection.activity

import androidx.appcompat.app.AppCompatActivity
import com.nunovalente.android.mypetagenda.util.CalendarImpl
import dagger.Module
import dagger.Provides
import java.util.*

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    fun activity() = activity

    @Provides
    fun fragmentManager(activity: AppCompatActivity) = activity.supportFragmentManager

    @ActivityScope
    @Provides
    fun calendar() = Calendar.getInstance()

    @ActivityScope
    @Provides
    fun calendarImpl(activity: AppCompatActivity, calendar: Calendar) = CalendarImpl(activity, calendar)
}