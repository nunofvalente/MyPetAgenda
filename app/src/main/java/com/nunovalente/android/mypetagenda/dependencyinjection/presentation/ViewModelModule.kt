package com.nunovalente.android.mypetagenda.dependencyinjection.presentation

import androidx.lifecycle.ViewModel
import com.nunovalente.android.mypetagenda.dependencyinjection.ViewModelKey
import com.nunovalente.android.mypetagenda.ui.poi.POIViewModel
import com.nunovalente.android.mypetagenda.ui.gallery.GalleryViewModel
import com.nunovalente.android.mypetagenda.ui.home.HomeViewModel
import com.nunovalente.android.mypetagenda.ui.map.MapsViewModel
import com.nunovalente.android.mypetagenda.ui.mypets.addpet.AddPetViewModel
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.notes.NotesViewModel
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.reminders.AddReminderViewModel
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.reminders.ReminderViewModel
import com.nunovalente.android.mypetagenda.ui.mypets.pets.MyPetsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun homeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GalleryViewModel::class)
    abstract fun galleryViewModel(galleryViewModel: GalleryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyPetsViewModel::class)
    abstract fun myPetsViewModel(myPetsViewModel: MyPetsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(POIViewModel::class)
    abstract fun activityViewModel(POIViewModel: POIViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddPetViewModel::class)
    abstract fun addPetViewModel(addPetViewModel: AddPetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddReminderViewModel::class)
    abstract fun addReminderViewModel(addReminderViewModel: AddReminderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotesViewModel::class)
    abstract fun notesViewModel(notesViewModel: NotesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReminderViewModel::class)
    abstract fun reminderViewModel(reminderViewModel: ReminderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapsViewModel::class)
    abstract fun mapsViewModel(mapsViewModel: MapsViewModel): ViewModel
}