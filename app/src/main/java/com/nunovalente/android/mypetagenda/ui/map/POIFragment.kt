package com.nunovalente.android.mypetagenda.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.*
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentPoiBinding
import com.nunovalente.android.mypetagenda.ui.common.ViewModelFactory
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
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

        val adView = binding.adViewMaps
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        return binding.root
    }

}