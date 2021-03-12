package com.nunovalente.android.mypetagenda.ui.mypets.petdetail

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.ChangeBounds
import androidx.transition.TransitionInflater
import com.google.android.material.transition.MaterialContainerTransform
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentPetDetailBinding
import com.nunovalente.android.mypetagenda.ui.common.ViewModelFactory
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
import javax.inject.Inject

class PetDetailFragment : BaseFragment() {

    @Inject lateinit var factory: ViewModelFactory

    private lateinit var viewModel: PetDetailViewModel
    private lateinit var binding: FragmentPetDetailBinding

    private val args: PetDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injector.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pet_detail, container, false)

        viewModel = ViewModelProvider(this, factory).get(PetDetailViewModel::class.java)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(Build.VERSION.SDK_INT >= 21) {
           sharedElementEnterTransition = MaterialContainerTransform().apply {
               duration = 500
           }
        }

        val pet = args.pet
        binding.pet = pet

        binding.executePendingBindings()
    }
}