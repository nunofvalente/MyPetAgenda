package com.nunovalente.android.mypetagenda.dependencyinjection.activity

import com.nunovalente.android.mypetagenda.dependencyinjection.presentation.PresentationComponent
import com.nunovalente.android.mypetagenda.dependencyinjection.presentation.PresentationModule
import dagger.Subcomponent

@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun newPresentationComponent(): PresentationComponent
}