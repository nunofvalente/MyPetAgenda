package com.nunovalente.android.mypetagenda.dependencyinjection.presentation

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.nunovalente.android.mypetagenda.data.NoteRepository
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.notes.NoteDialogImpl
import com.nunovalente.android.mypetagenda.ui.gallery.CameraUseCase
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    @Provides
    fun dialog(repository: NoteRepository, activity: AppCompatActivity, inflater: LayoutInflater) = NoteDialogImpl(repository,activity, inflater)

    @Provides
    fun cameraUseCase(activity: AppCompatActivity) = CameraUseCase(activity)
}