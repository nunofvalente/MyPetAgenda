package com.nunovalente.android.mypetagenda.ui.mypets.pets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentMypetsBinding
import com.nunovalente.android.mypetagenda.ui.common.ViewModelFactory
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
import com.nunovalente.android.mypetagenda.ui.mypets.MyPetsAdapter
import com.nunovalente.android.mypetagenda.ui.mypets.PetClickListener
import javax.inject.Inject

class MyPetsFragment : BaseFragment() {

    @Inject lateinit var factory: ViewModelFactory

    private lateinit var viewModel: MyPetsViewModel
    private lateinit var binding: FragmentMypetsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injector.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mypets, container, false)
        viewModel = ViewModelProvider(this, factory).get(MyPetsViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        viewModel.navigate.observe(viewLifecycleOwner, { navigate ->
            if (navigate) {
                findNavController().navigate(R.id.action_navigation_mypets_to_addPetFragment)
                viewModel.doneNavigating()
            }
        })

        viewModel.navigateToDetails.observe(viewLifecycleOwner, { pet ->
            if(pet != null) {
                findNavController().navigate(R.id.action_navigation_mypets_to_petDetailFragment)
                viewModel.doneNavigating()
            }
        })

        setRecyclerAdapter()

        viewModel.petList.observe(viewLifecycleOwner, { petList ->
            if (petList!!.isEmpty()) {
                showNoData()
            } else {
                showData()
            }
        })

        return binding.root
    }

    private fun setRecyclerAdapter() {
        binding.recyclerMyPets.apply {
            this.adapter = MyPetsAdapter(PetClickListener { pet ->
                viewModel.navigateToPetDetail(pet)
            })
        }
    }

    private fun showData() {
        binding.recyclerMyPets.visibility = View.VISIBLE
        binding.txtMypetsNoData.visibility = View.GONE
    }

    private fun showNoData() {
        binding.recyclerMyPets.visibility = View.GONE
        binding.txtMypetsNoData.visibility = View.VISIBLE
    }
}