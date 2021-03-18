package com.nunovalente.android.mypetagenda.ui.gallery

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.camera.core.ImageCapture
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentGalleryBinding
import com.nunovalente.android.mypetagenda.ui.common.ViewModelFactory
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
import com.nunovalente.android.mypetagenda.ui.mypets.addpet.AddPetFragment
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

class GalleryFragment : BaseFragment() {

    @Inject lateinit var factory: ViewModelFactory

    private lateinit var binding: FragmentGalleryBinding
    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injector.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)

        binding.fabCamera.setOnClickListener {
          findNavController().navigate(R.id.action_navigation_gallery_to_cameraFragment)
        }

        galleryViewModel = ViewModelProvider(this, factory).get(GalleryViewModel::class.java)

        return binding.root

        //TODO Make FAB disappear when changing fragments
    }
}