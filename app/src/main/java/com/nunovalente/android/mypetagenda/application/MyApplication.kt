package com.nunovalente.android.mypetagenda.application

import android.app.Application
import com.nunovalente.android.mypetagenda.dependencyinjection.application.*
import com.nunovalente.android.mypetagenda.util.createChannel
import timber.log.Timber

class MyApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .databaseModule(DatabaseModule())
            .retrofitModule(RetrofitModule(this))
            .build()

        //Creates notification channel
        createChannel(this)

        Timber.plant(Timber.DebugTree())
    }
}