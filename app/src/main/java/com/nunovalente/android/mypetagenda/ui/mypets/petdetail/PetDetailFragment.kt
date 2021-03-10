package com.nunovalente.android.mypetagenda.ui.mypets.petdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentPetDetailBinding
import com.nunovalente.android.mypetagenda.ui.common.ViewModelFactory
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
import javax.inject.Inject

class PetDetailFragment : BaseFragment() {

    @Inject lateinit var factory: ViewModelFactory

    private lateinit var viewModel: PetDetailViewModel
    private lateinit var binding: FragmentPetDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injector.inject(this)
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pet_detail, container, false)



        return binding.root
    }
}