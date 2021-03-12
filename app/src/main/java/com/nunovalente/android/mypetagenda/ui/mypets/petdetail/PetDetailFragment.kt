package com.nunovalente.android.mypetagenda.ui.mypets.petdetail

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.ChangeBounds
import androidx.transition.TransitionInflater
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.transition.MaterialContainerTransform
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentPetDetailBinding
import com.nunovalente.android.mypetagenda.ui.common.ViewModelFactory
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.NotesFragment
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.ProfileFragment
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.RemindersFragment
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
        if (Build.VERSION.SDK_INT >= 21) {
            sharedElementEnterTransition = MaterialContainerTransform().apply {
                duration = 300
            }
        }

        val pet = args.pet
        binding.pet = pet
        binding.executePendingBindings()

        binding.petDetailTab.setupWithViewPager(binding.pagerPetProfile)
        binding.pagerPetProfile.adapter = PetViewPagerAdapter(parentFragmentManager)
    }
}

    class PetViewPagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        //Number of views in the Pager
        override fun getCount(): Int {
            return 3
        }

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> RemindersFragment.newInstance()
                1 -> NotesFragment.newInstance()
                else -> ProfileFragment.newInstance()
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when(position) {
                0 -> "Reminders"
                1 -> "Notes"
                else -> "Profile"
            }
        }
    }