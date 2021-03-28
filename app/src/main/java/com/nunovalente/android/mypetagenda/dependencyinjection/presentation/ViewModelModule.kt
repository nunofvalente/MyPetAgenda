package com.nunovalente.android.mypetagenda.dependencyinjection.presentation

import androidx.lifecycle.ViewModel
import com.nunovalente.android.mypetagenda.dependencyinjection.ViewModelKey
import com.nunovalente.android.mypetagenda.ui.activity.ActivityViewModel
import com.nunovalente.android.mypetagenda.ui.gallery.GalleryViewModel
import com.nunovalente.android.mypetagenda.ui.home.HomeViewModel
import com.nunovalente.android.mypetagenda.ui.mypets.addpet.AddPetViewModel
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.notes.NotesViewModel
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.reminders.AddReminderViewModel
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
    @ViewModelKey(ActivityViewModel::class)
    abstract fun activityViewModel(activityViewModel: ActivityViewModel): ViewModel

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
}