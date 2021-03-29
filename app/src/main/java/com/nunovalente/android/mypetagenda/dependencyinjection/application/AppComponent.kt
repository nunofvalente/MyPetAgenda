package com.nunovalente.android.mypetagenda.dependencyinjection.application

import com.nunovalente.android.mypetagenda.dependencyinjection.activity.ActivityComponent
import com.nunovalente.android.mypetagenda.dependencyinjection.activity.ActivityModule
import com.nunovalente.android.mypetagenda.notif.ReminderBroadcastReceiver
import dagger.Component

@AppScope
@Component(modules = [AppModule::class, DatabaseModule::class])
interface AppComponent {

    fun newActivityComponent(activityModule: ActivityModule): ActivityComponent
}