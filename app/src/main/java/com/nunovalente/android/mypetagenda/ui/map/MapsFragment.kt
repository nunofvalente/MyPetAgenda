package com.nunovalente.android.mypetagenda.ui.map

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentMapsBinding
import com.nunovalente.android.mypetagenda.ui.common.ViewModelFactory
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
import com.nunovalente.android.mypetagenda.util.Constants.GROOMING
import com.nunovalente.android.mypetagenda.util.Constants.PARK
import com.nunovalente.android.mypetagenda.util.Constants.PET_SHOPS
import com.nunovalente.android.mypetagenda.util.Constants.VETERINARIAN
import com.nunovalente.android.mypetagenda.util.StringUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.coroutineContext


class MapsFragment : BaseFragment(), OnMapReadyCallback {

    @Inject
    lateinit var factory: ViewModelFactory

    companion object {
        private val AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111"

        private const val REQUEST_CODE_PERMISSIONS = 2
        private val REQUIRED_PERMISSIONS = arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private lateinit var binding: FragmentMapsBinding
    private lateinit var viewModel: MapsViewModel
    private lateinit var adView: AdView
    private lateinit var map: GoogleMap

    private val args: MapsFragmentArgs by navArgs()
    private var searchParameter: String = ""
    private lateinit var currentLocation: Location

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injector.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_maps, container, false)

        viewModel = ViewModelProvider(this, factory).get(MapsViewModel::class.java)

        //Initialize ads
        MobileAds.initialize(requireActivity())

        adView = AdView(requireActivity())
        adView.adUnitId = AD_UNIT_ID
        binding.containerAdViewMaps.addView(adView)
        loadBanner()

        //Retrieve search parameter to obtain places
        searchParameter = args.searchParameter

        //Initialise location provider client
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        viewModel.places.observe(viewLifecycleOwner, { resultList ->
            if (resultList != null) {
                for (place in resultList) {
                    map.addMarker(
                        MarkerOptions().position(
                            LatLng(
                                place.geometry?.location?.lat!!,
                                place.geometry?.location?.lng!!
                            )
                        ).title(place.name)
                    )
                }
            }
        })

        setToolbarTitle(searchParameter)
        setListeners()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isPermissionGranted()) {
            startMap()
        } else {
            requestPermissions(
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun setToolbarTitle(title: String) {
        when (title) {
            VETERINARIAN -> binding.toolbarMapTitle.text = VETERINARIAN
            PET_SHOPS -> binding.toolbarMapTitle.text = PET_SHOPS
            GROOMING -> binding.toolbarMapTitle.text = GROOMING
            PARK -> binding.toolbarMapTitle.text = PARK
        }
    }

    private fun setListeners() {
        binding.mapBackArrow.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun loadBanner() {
        val adRequest = AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .build()

        val adSize: AdSize = getAdSize()
        adView.adSize = adSize



        adView.loadAd(adRequest)

    }

    private fun getAdSize(): AdSize {
        val outMetrics = DisplayMetrics()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val display = requireActivity().display
            display?.getRealMetrics(outMetrics)
        } else {
            @Suppress("DEPRECATION")
            val display = requireActivity().windowManager?.defaultDisplay
            @Suppress("DEPRECATION")
            display?.getMetrics(outMetrics)
        }

        val density = outMetrics.density

        var adWidthPixels = binding.containerAdViewMaps.width.toFloat()
        if (adWidthPixels == 0f) {
            adWidthPixels = outMetrics.widthPixels.toFloat()
        }

        val adWidth = (adWidthPixels / density).toInt()
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(requireActivity(), adWidth)
    }

    private fun isPermissionGranted(): Boolean = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireActivity(),
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (isPermissionGranted()) {
                startMap()
            } else {
                findNavController().navigateUp()
            }
        }
    }

    private fun startMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            currentLocation = location
            viewModel.loadPlaces(StringUtil.locationToString(location), 2000, searchParameter)

            val myLocation = LatLng(location.latitude, location.longitude)
            val zoomLevel = 14.0f
            map.isMyLocationEnabled = true
            map.uiSettings.isMyLocationButtonEnabled = true
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, zoomLevel))
        }
    }
}