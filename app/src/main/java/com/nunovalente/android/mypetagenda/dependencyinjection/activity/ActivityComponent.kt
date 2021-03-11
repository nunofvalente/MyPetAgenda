package com.nunovalente.android.mypetagenda.dependencyinjection.activity

import com.nunovalente.android.mypetagenda.dependencyinjection.presentation.PresentationComponent
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun newPresentationComponent(): PresentationComponent
}