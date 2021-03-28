package com.nunovalente.android.mypetagenda.dependencyinjection.application

import android.app.AlarmManager
import android.content.Context
import com.nunovalente.android.mypetagenda.application.MyApplication
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: MyApplication) {

    @Provides
    fun application() = application

    @Provides
    fun alarmManager(): AlarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager
}