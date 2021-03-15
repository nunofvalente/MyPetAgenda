package com.nunovalente.android.mypetagenda.dependencyinjection.presentation

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.nunovalente.android.mypetagenda.data.DefaultNoteRepository
import com.nunovalente.android.mypetagenda.data.NoteRepository
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.adapters.PetViewPagerAdapter
import com.nunovalente.android.mypetagenda.util.NoteDialogImpl
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    @Provides
    fun petViewPagerAdapter(fragmentManager: FragmentManager) = PetViewPagerAdapter(fragmentManager)

    @Provides
    fun dialog(repository: NoteRepository, activity: AppCompatActivity, inflater: LayoutInflater) = NoteDialogImpl(repository,activity, inflater)
}