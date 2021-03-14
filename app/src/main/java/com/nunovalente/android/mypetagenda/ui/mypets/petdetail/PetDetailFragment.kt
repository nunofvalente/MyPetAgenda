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
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentPetDetailBinding
import com.nunovalente.android.mypetagenda.ui.common.ViewModelFactory
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.adapters.PetViewPagerAdapter
import javax.inject.Inject


class PetDetailFragment : BaseFragment() {

    @Inject lateinit var factory: ViewModelFactory
    @Inject lateinit var petViewPagerAdapter: PetViewPagerAdapter

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
                duration = 600
            }
        }

        val pet = args.pet
        binding.pet = pet
        binding.executePendingBindings()

        setViewPager()
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
}