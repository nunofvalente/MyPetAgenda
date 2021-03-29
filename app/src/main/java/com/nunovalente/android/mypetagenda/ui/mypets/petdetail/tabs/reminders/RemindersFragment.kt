package com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.reminders

import android.app.AlarmManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentRemindersBinding
import com.nunovalente.android.mypetagenda.models.Reminder
import com.nunovalente.android.mypetagenda.ui.common.ViewModelFactory
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.PetDetailViewModel
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.notes.NotesViewModel
import com.nunovalente.android.mypetagenda.util.ReminderUtil
import javax.inject.Inject

class RemindersFragment : BaseFragment(), OnToggleReminderListener {

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var alarmManager: AlarmManager

    private lateinit var binding: FragmentRemindersBinding

    private lateinit var viewModelDetail: PetDetailViewModel
    private lateinit var viewModel: ReminderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injector.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reminders, container, false)

        viewModel = ViewModelProvider(this, factory).get(ReminderViewModel::class.java)
        viewModelDetail =
            ViewModelProvider(requireActivity(), factory).get(PetDetailViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModelDetail.getPetRetrieved()?.apply {
            viewModel.loadReminders(this.id)
        }

        viewModel.reminderList?.observe(viewLifecycleOwner, { notes ->
            if (notes.isNullOrEmpty()) {
                showTextNoReminders()
            } else {
                hideTextNoReminders()
            }
        })

        setRecyclerAdapter()

        return binding.root
    }

    private fun setRecyclerAdapter() {
        binding.recyclerReminders.let {
            it.adapter = ReminderAdapter(this)
        }
    }

    private fun hideTextNoReminders() {
        binding.tvNoReminders.visibility = View.GONE
        binding.recyclerReminders.visibility = View.VISIBLE
    }

    private fun showTextNoReminders() {
        binding.tvNoReminders.visibility = View.VISIBLE
        binding.recyclerReminders.visibility = View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance() = RemindersFragment()
    }

    override fun onToggle(reminder: Reminder?) {
        Toast.makeText(requireActivity(), "Test", Toast.LENGTH_LONG).show()
          if (reminder != null) {
              if (reminder.isStarted) {
                  ReminderUtil.cancelReminder(requireActivity(), reminder)
                  viewModel.updateReminder(reminder)
              } else {
                  ReminderUtil.scheduleReminder(requireActivity(), reminder ,alarmManager)
                  viewModel.updateReminder(reminder)
              }
          }
      }
}