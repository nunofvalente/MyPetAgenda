package com.nunovalente.android.mypetagenda.ui.common.fragment

import androidx.fragment.app.Fragment
import com.nunovalente.android.mypetagenda.dependencyinjection.presentation.PresentationComponent
import com.nunovalente.android.mypetagenda.ui.common.activity.BaseActivity

open class BaseFragment: Fragment() {

    private val presentationComponent: PresentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent.newPresentationComponent()
    }

    protected val injector: PresentationComponent get() = presentationComponent
}