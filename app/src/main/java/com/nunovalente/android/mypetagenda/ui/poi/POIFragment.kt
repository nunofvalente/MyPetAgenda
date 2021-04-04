package com.nunovalente.android.mypetagenda.ui.poi

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.*
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentPoiBinding
import com.nunovalente.android.mypetagenda.ui.common.ViewModelFactory
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
import com.nunovalente.android.mypetagenda.util.Constants.GROOMING
import com.nunovalente.android.mypetagenda.util.Constants.PARK
import com.nunovalente.android.mypetagenda.util.Constants.PET_SHOPS
import com.nunovalente.android.mypetagenda.util.Constants.VETERINARIAN
import java.security.Permission
import javax.inject.Inject

class POIFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var viewModel: POIViewModel
    private lateinit var binding: FragmentPoiBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injector.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_poi, container, false)

        viewModel = ViewModelProvider(this, factory).get(POIViewModel::class.java)

        MobileAds.initialize(requireActivity())

        val adView = binding.adViewPoi
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)


        binding.cardVeterinarian.setOnClickListener {
            val directions = POIFragmentDirections.actionNavigationPoiToMapsFragment(VETERINARIAN)
            findNavController().navigate(directions)
        }

        binding.cardPetShops.setOnClickListener {
            val directions = POIFragmentDirections.actionNavigationPoiToMapsFragment(PET_SHOPS)
            findNavController().navigate(directions)
        }

        binding.cardGrooming.setOnClickListener {
            val directions = POIFragmentDirections.actionNavigationPoiToMapsFragment(GROOMING)
            findNavController().navigate(directions)

        }

        binding.cardPark.setOnClickListener {
            val directions = POIFragmentDirections.actionNavigationPoiToMapsFragment(PARK)
            findNavController().navigate(directions)
        }

        return binding.root
    }
}