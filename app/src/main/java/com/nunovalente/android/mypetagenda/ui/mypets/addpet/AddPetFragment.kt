package com.nunovalente.android.mypetagenda.ui.mypets.addpet

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.nunovalente.android.mypetagenda.util.Constants
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentAddPetBinding
import com.nunovalente.android.mypetagenda.ui.common.ViewModelFactory
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
import com.nunovalente.android.mypetagenda.ui.mypets.AddPetViewModel
import com.nunovalente.android.mypetagenda.util.CalendarImpl
import javax.inject.Inject

class AddPetFragment : BaseFragment() {

    companion object {
        private const val READ_EXTERNAL_STORAGE_ID = 0
    }

    @Inject lateinit var factory: ViewModelFactory
    @Inject lateinit var calendarImpl: CalendarImpl

    private val permissionList = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

    private lateinit var viewModel: AddPetViewModel
    private lateinit var binding: FragmentAddPetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injector.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_pet, container, false)

        viewModel = ViewModelProvider(this, factory).get(AddPetViewModel::class.java)
        binding.viewModel = viewModel

        //Sets the spinner value
        binding.spinnerMyPets.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.pet.value?.type = binding.spinnerMyPets.selectedItem as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.pet.value?.type = binding.spinnerMyPets.selectedItem as String
            }
        }

        binding.addPetBackArrow.setOnClickListener {
            findNavController().navigateUp()
        }

        //Observes when to navigate
        viewModel.navigate.observe(viewLifecycleOwner, { navigate ->
            if (navigate) {
                findNavController().navigate(R.id.action_navigation_addPetFragment_to_navigation_mypets)
                viewModel.doneNavigating()
            }
        })

        setListeners()

        return binding.root
    }

    private fun setListeners() {
        binding.textChangeProfilePhoto.setOnClickListener {
            if (isPermissionGranted()) {
                uploadPhoto()
            } else {
                requestPermission()
            }
        }

        binding.editPetBirthday.setOnClickListener {
            calendarImpl.chooseDate(requireContext(), binding.editPetBirthday)
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            permissionList,
            READ_EXTERNAL_STORAGE_ID
        )
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == READ_EXTERNAL_STORAGE_ID) {
            if(isPermissionGranted()) {
                uploadPhoto()
            } else {
                return
            }
        }
    }

    private fun uploadPhoto() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, Constants.GALLERY_REQ_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == Constants.GALLERY_REQ_CODE && resultCode == Activity.RESULT_OK) {
            if(data == null) {
                return
            }

            val imageSelectedUri = data.data
            Glide.with(this).load(imageSelectedUri).circleCrop().into(binding.imageAddPet);
            viewModel.pet.value?.imagePath = imageSelectedUri.toString()
        }
    }
}