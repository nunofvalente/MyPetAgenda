package com.nunovalente.android.mypetagenda.ui.mypets.addpet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.application.MyApplication
import com.nunovalente.android.mypetagenda.databinding.FragmentAddPetBinding
import com.nunovalente.android.mypetagenda.ui.common.ViewModelFactory
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
import com.nunovalente.android.mypetagenda.ui.mypets.AddPetViewModel
import com.nunovalente.android.mypetagenda.ui.mypets.AddPetViewModelFactory
import javax.inject.Inject

class AddPetFragment : BaseFragment() {

    @Inject lateinit var factory: ViewModelFactory

    private lateinit var viewModel: AddPetViewModel
    private lateinit var binding: FragmentAddPetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injector.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_pet, container, false)

        viewModel = ViewModelProvider(this, factory).get(AddPetViewModel::class.java)
        binding.viewModel = viewModel

        binding.spinnerMyPets.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.pet.value?.type = binding.spinnerMyPets.selectedItem as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.pet.value?.type = binding.spinnerMyPets.selectedItem as String
            }
        }

        viewModel.navigate.observe(viewLifecycleOwner, { navigate ->
            if (navigate) {
                findNavController().navigate(R.id.action_navigation_addPetFragment_to_navigation_mypets)
                viewModel.doneNavigating()
            }
        })


            return binding.root
    }
}