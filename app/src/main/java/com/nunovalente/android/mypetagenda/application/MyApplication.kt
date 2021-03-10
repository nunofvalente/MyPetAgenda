package com.nunovalente.android.mypetagenda.application

import android.app.Application
import com.nunovalente.android.mypetagenda.dependencyinjection.application.AppComponent
import com.nunovalente.android.mypetagenda.dependencyinjection.application.AppModule
import com.nunovalente.android.mypetagenda.dependencyinjection.application.DaggerAppComponent
import com.nunovalente.android.mypetagenda.dependencyinjection.application.DatabaseModule
import timber.log.Timber

class MyApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this),)
            .databaseModule(DatabaseModule())
            .build()

        Timber.plant(Timber.DebugTree())
    }
}