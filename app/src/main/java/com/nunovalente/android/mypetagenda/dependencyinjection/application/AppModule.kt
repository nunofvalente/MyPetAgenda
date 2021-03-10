package com.nunovalente.android.mypetagenda.dependencyinjection.application

import com.nunovalente.android.mypetagenda.application.MyApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: MyApplication) {

    @Provides
    fun application() = application
}