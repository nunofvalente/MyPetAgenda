package com.nunovalente.android.mypetagenda.dependencyinjection.presentation

import androidx.fragment.app.FragmentManager
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.adapters.PetViewPagerAdapter
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    @Provides
    fun petViewPagerAdapter(fragmentManager: FragmentManager) = PetViewPagerAdapter(fragmentManager)
}