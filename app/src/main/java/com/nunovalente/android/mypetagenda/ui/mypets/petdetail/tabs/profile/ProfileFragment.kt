package com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentProfileBinding
import com.nunovalente.android.mypetagenda.ui.common.ViewModelFactory
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.PetDetailViewModel
import javax.inject.Inject

class ProfileFragment : BaseFragment() {

    @Inject lateinit var factory: ViewModelFactory

    private lateinit var binding: FragmentProfileBinding

    private lateinit var viewModelDetail: PetDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injector.inject(this)
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        viewModelDetail = ViewModelProvider(requireActivity(), factory).get(PetDetailViewModel::class.java)

        binding.pet = viewModelDetail.getPetRetrieved()

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}