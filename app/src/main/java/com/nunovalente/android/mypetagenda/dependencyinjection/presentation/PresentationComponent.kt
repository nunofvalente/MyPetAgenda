package com.nunovalente.android.mypetagenda.dependencyinjection.presentation

import com.nunovalente.android.mypetagenda.ui.activity.ActivityFragment
import com.nunovalente.android.mypetagenda.ui.gallery.GalleryFragment
import com.nunovalente.android.mypetagenda.ui.home.HomeFragment
import com.nunovalente.android.mypetagenda.ui.mypets.addpet.AddPetFragment
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.PetDetailFragment
import com.nunovalente.android.mypetagenda.ui.mypets.pets.MyPetsFragment
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class, PresentationModule::class])
interface PresentationComponent {
    fun inject(fragment: HomeFragment)
    fun inject(fragment: ActivityFragment)
    fun inject(fragment: GalleryFragment)
    fun inject(fragment: MyPetsFragment)
    fun inject(fragment: AddPetFragment)
    fun inject(fragment: PetDetailFragment)
}