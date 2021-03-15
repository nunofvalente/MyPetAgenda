package com.nunovalente.android.mypetagenda.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentGalleryBinding
import com.nunovalente.android.mypetagenda.ui.common.ViewModelFactory
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
import javax.inject.Inject

class GalleryFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var binding: FragmentGalleryBinding
    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        injector.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)

        galleryViewModel = ViewModelProvider(this, factory).get(GalleryViewModel::class.java)

        return binding.root

        //TODO Make FAB disappear when changing fragments
    }
}