package com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.reminders

import android.app.Activity
import android.app.AlarmManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentAddReminderBinding
import com.nunovalente.android.mypetagenda.models.Pet
import com.nunovalente.android.mypetagenda.ui.common.ViewModelFactory
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
import com.nunovalente.android.mypetagenda.util.CalendarImpl
import com.nunovalente.android.mypetagenda.util.ReminderUtil
import com.nunovalente.android.mypetagenda.util.TimePickerUtil
import javax.inject.Inject


class AddReminderFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var calendarImpl: CalendarImpl

    @Inject
    lateinit var alarmManager: AlarmManager

    private lateinit var viewModel: AddReminderViewModel
    private lateinit var binding: FragmentAddReminderBinding

    private val args: AddReminderFragmentArgs by navArgs()
    private lateinit var pet: Pet

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injector.inject(this)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_reminder, container, false)

        viewModel = ViewModelProvider(this, factory).get(AddReminderViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        pet = args.pet

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Sets Shared Element Transition name
        if (Build.VERSION.SDK_INT >= 21) {
            binding.coordinatorAddReminder.transitionName = getString(R.string.view_transitioned)
        }

        setTransitions()
        setListeners()
    }

    /**
     * Sets all listeners in the layout
     */
    private fun setListeners() {
        binding.imageNavigateBack.setOnClickListener {
            findNavController().navigate(
                AddReminderFragmentDirections.actionNavigationAddReminderFragmentToNavigationPetDetailFragment(
                    pet
                )
            )
        }

        binding.checkboxRecurring.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                binding.linearWeekDays.visibility = View.GONE
                binding.reminderDate.visibility = View.VISIBLE
                viewModel.setNotRecurring()
            } else {
                binding.linearWeekDays.visibility = View.VISIBLE
                binding.reminderDate.visibility = View.GONE
                viewModel.setRecurring()
            }
        }

        binding.reminderDate.setOnClickListener {
            calendarImpl.chooseDateReminder(requireContext(), binding.reminderDate)
        }

        binding.imageAddReminder.setOnClickListener {
            if (validateReminder()) {
                hideSoftKeyboard(requireActivity())
                ReminderUtil.scheduleReminder(
                    requireActivity(),
                    viewModel.reminder.value!!,
                    alarmManager
                )
                viewModel.startReminder()
                viewModel.saveReminder()
                findNavController().navigateUp()
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Please fill the information!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun validateReminder(): Boolean {
        if (!binding.editReminderTitle.text.isNullOrEmpty()) {
            viewModel.setInfo(
                pet.id,
                pet.name,
                TimePickerUtil.getTimePickerMinute(binding.reminderTimePicker),
                TimePickerUtil.getTimePickerHour(binding.reminderTimePicker),
                binding.reminderDate.text.toString()
            )
            return true
        }
        return false
    }

    /**
     * Handles Shared Element Transition
     */
    @Suppress("DEPRECATION")
    private fun setTransitions() {
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = resources.getInteger(R.integer.transition_duration_large).toLong()
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().resources.getColor(R.color.background_color))
        }
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager.isAcceptingText) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                0
            )
        }
    }
}