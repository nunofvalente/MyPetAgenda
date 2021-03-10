package com.nunovalente.android.mypetagenda.ui.common.activity

import androidx.appcompat.app.AppCompatActivity
import com.nunovalente.android.mypetagenda.application.MyApplication
import com.nunovalente.android.mypetagenda.dependencyinjection.activity.ActivityComponent
import com.nunovalente.android.mypetagenda.dependencyinjection.activity.ActivityModule
import com.nunovalente.android.mypetagenda.dependencyinjection.presentation.PresentationComponent

open class BaseActivity: AppCompatActivity() {

    val activityComponent: ActivityComponent by lazy {
        (application as MyApplication).appComponent.newActivityComponent(ActivityModule(this))
    }

    private val presentationComponent: PresentationComponent by lazy {
        activityComponent.newPresentationComponent()
    }

    protected val injector: PresentationComponent get() = presentationComponent
}