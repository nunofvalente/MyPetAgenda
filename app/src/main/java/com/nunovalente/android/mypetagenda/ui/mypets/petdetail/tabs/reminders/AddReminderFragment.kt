package com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.reminders

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentAddReminderBinding
import com.nunovalente.android.mypetagenda.models.Pet
import com.nunovalente.android.mypetagenda.models.getRecurringDays
import com.nunovalente.android.mypetagenda.notif.ReminderBroadcastReceiver
import com.nunovalente.android.mypetagenda.ui.common.ViewModelFactory
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
import com.nunovalente.android.mypetagenda.util.CalendarImpl
import com.nunovalente.android.mypetagenda.util.Constants.REMINDER
import com.nunovalente.android.mypetagenda.util.TimePickerUtil
import java.util.*
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
            findNavController().navigateUp()
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
                scheduleReminder()
                findNavController().navigate(
                    AddReminderFragmentDirections.actionNavigationAddReminderFragmentToNavigationPetDetailFragment(
                        pet
                    )
                )
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
        if(!binding.titleAddReminder.text.isNullOrEmpty()) {
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

    /**
     * Deals with scheduling the reminder
     */
    private fun scheduleReminder() {
        val reminder = viewModel.reminder.value

        val intent = Intent(requireActivity(), ReminderBroadcastReceiver::class.java).apply {
            putExtra(REMINDER, reminder)
        }

        val pendingIntent = PendingIntent.getBroadcast(requireActivity(), reminder!!.id, intent, 0)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.DAY_OF_MONTH, reminder.date.substring(0, 1).toInt())
        calendar.set(Calendar.MONTH, reminder.date.substring(3, 4).toInt())
        calendar.set(Calendar.HOUR_OF_DAY, reminder.hour)
        calendar.set(Calendar.MINUTE, reminder.minutes)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
        }

        if(!reminder.isRecurring) {
            var toastString: String? = null
            try {
                toastString = String.format("Alarm for %s has been set", reminder.title)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            Toast.makeText(requireActivity(), toastString, Toast.LENGTH_SHORT).show()

            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else {
            val toastText = String.format(
                "Recurring Alarm %s scheduled for %s at %02d:%02d",
                reminder.title,
                reminder.getRecurringDays(),
                reminder.hour,
                reminder.minutes,
                reminder.id
            )
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()

            val RUN_DAILY = (24 * 60 * 60 * 1000).toLong()
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                RUN_DAILY,
                pendingIntent
            )
        }

        viewModel.startReminder()
    }
}