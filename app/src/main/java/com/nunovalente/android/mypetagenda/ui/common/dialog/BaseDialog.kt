package com.nunovalente.android.mypetagenda.ui.common.dialog

import androidx.fragment.app.DialogFragment
import com.nunovalente.android.mypetagenda.ui.common.activity.BaseActivity

open class BaseDialog: DialogFragment() {

    private val presentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent.newPresentationComponent()
    }

    protected val injector get() = presentationComponent
}