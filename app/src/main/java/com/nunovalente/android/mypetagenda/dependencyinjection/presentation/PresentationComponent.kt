package com.nunovalente.android.mypetagenda.dependencyinjection.presentation

import com.nunovalente.android.mypetagenda.ui.poi.POIFragment
import com.nunovalente.android.mypetagenda.ui.gallery.CameraFragment
import com.nunovalente.android.mypetagenda.ui.gallery.GalleryFragment
import com.nunovalente.android.mypetagenda.ui.home.HomeFragment
import com.nunovalente.android.mypetagenda.ui.map.MapsFragment
import com.nunovalente.android.mypetagenda.ui.mypets.addpet.AddPetFragment
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.PetDetailFragment
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.notes.NotesFragment
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.reminders.AddReminderFragment
import com.nunovalente.android.mypetagenda.ui.mypets.pets.MyPetsFragment
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.notes.NoteDialogImpl
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.profile.ProfileFragment
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.reminders.RemindersFragment
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class, PresentationModule::class])
interface PresentationComponent {
    fun inject(fragment: HomeFragment)
    fun inject(fragment: POIFragment)
    fun inject(fragment: GalleryFragment)
    fun inject(fragment: MyPetsFragment)
    fun inject(fragment: AddPetFragment)
    fun inject(fragment: PetDetailFragment)
    fun inject(fragment: AddReminderFragment)
    fun inject(fragment: NotesFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: RemindersFragment)
    fun inject(fragment: MapsFragment)

    fun inject(fragment: CameraFragment)
    fun inject(dialog: NoteDialogImpl)
}