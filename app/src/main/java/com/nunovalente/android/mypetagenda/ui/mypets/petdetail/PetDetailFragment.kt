package com.nunovalente.android.mypetagenda.ui.mypets.petdetail

import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentPetDetailBinding
import com.nunovalente.android.mypetagenda.models.Pet
import com.nunovalente.android.mypetagenda.ui.common.ViewModelFactory
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.adapters.PetViewPagerAdapter
import com.nunovalente.android.mypetagenda.util.Constants
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.notes.NoteDialogImpl
import javax.inject.Inject


class PetDetailFragment : BaseFragment() {

    companion object {
        private val TAG: String = PetDetailFragment::class.java.simpleName
    }

    @Inject
    lateinit var petViewPagerAdapter: PetViewPagerAdapter
    @Inject
    lateinit var dialog: NoteDialogImpl

    private lateinit var viewModel: PetDetailViewModel
    private lateinit var binding: FragmentPetDetailBinding
    private lateinit var pet: Pet

    private val args: PetDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injector.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pet_detail, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(PetDetailViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pet = args.pet!!
        binding.pet = pet
        viewModel.setPetValue(pet)
        binding.executePendingBindings()


        binding.frameViewPager.elevation = resources.getDimension(R.dimen.view_pager_elevation)
        binding.petProfileImage.transitionName = pet.name
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 600
        }
        binding.menu.transitionName =
            resources.getString(R.string.fab_transition_to_view)
    }

    private fun setListeners() {
        binding.fabPetReminder.setOnClickListener {
            exitTransition = Hold().apply {
                duration = resources.getInteger(R.integer.transition_duration_large).toLong()
            }
            reenterTransition = Hold().apply {
                duration = resources.getInteger(
                    R.integer.transition_duration_large
                ).toLong()
            }
            val extras = FragmentNavigatorExtras(
                binding.menu to resources.getString(R.string.view_transitioned)
            )

            val directions =
                PetDetailFragmentDirections.actionNavigationPetDetailFragmentToAddReminderFragment(pet)
            findNavController().navigate(directions, extras)
        }

        binding.fabPetNotes.setOnClickListener {
            dialog.isCancelable = false
            val args = Bundle()
            args.putInt(Constants.PET_ID, pet.id)
            dialog.arguments = args
            dialog.show(parentFragmentManager, TAG)
        }
    }

    private fun setViewPager() {
        binding.pagerPetProfile.adapter = petViewPagerAdapter
        binding.petDetailTab.setupWithViewPager(binding.pagerPetProfile)

        val root = binding.petDetailTab.getChildAt(0)
        if (root is LinearLayout) {
            root.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
            val drawable = GradientDrawable()
            drawable.setColor(ContextCompat.getColor(requireActivity(), R.color.app_primary_dark))
            drawable.setSize(5, 1)
            root.dividerPadding = 30
            root.dividerDrawable = drawable
        }

    }

    override fun onResume() {
        super.onResume()
        setListeners()
        setViewPager()
    }
}