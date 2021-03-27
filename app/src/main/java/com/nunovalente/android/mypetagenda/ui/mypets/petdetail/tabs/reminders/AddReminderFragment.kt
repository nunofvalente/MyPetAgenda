package com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.reminders

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.Fade
import com.google.android.material.transition.MaterialContainerTransform
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentAddReminderBinding
import com.nunovalente.android.mypetagenda.models.Pet
import com.nunovalente.android.mypetagenda.ui.common.ViewModelFactory
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
import com.nunovalente.android.mypetagenda.util.CalendarImpl
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import javax.inject.Inject

class AddReminderFragment : BaseFragment() {

    @Inject lateinit var factory: ViewModelFactory
    @Inject lateinit var calendarImpl: CalendarImpl

    private lateinit var viewModel: AddReminderViewModel
    private lateinit var binding: FragmentAddReminderBinding

    private lateinit var reminderDate: EditText
    private lateinit var linearLayoutDays: LinearLayout

    private val args: AddReminderFragmentArgs by navArgs()
    private lateinit var pet: Pet

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injector.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_reminder, container, false)

        viewModel = ViewModelProvider(this, factory).get(AddReminderViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        pet = args.pet

        reminderDate = binding.reminderDate
        linearLayoutDays = binding.linearDays


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= 21) {
            binding.coordinatorAddReminder.transitionName = getString(R.string.view_transitioned)
        }

        setTransitions()
        setListeners()
    }

    private fun setListeners() {
        binding.imageNavigateBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.checkboxRecurring.setOnCheckedChangeListener { _, isChecked ->
            if(!isChecked) {
                linearLayoutDays.visibility = View.GONE
                reminderDate.visibility = View.VISIBLE
            } else {
                binding.linearDays.visibility = View.VISIBLE
                reminderDate.visibility = View.GONE
                reminderDate.setText("")
            }
        }

        binding.reminderDate.setOnClickListener {
            calendarImpl.chooseDateReminder(requireContext(), binding.reminderDate)
        }
    }

    @Suppress("DEPRECATION")
    private fun setTransitions() {
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = resources.getInteger(R.integer.transition_duration_large).toLong()
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().resources.getColor(R.color.background_color))
        }
    }
}