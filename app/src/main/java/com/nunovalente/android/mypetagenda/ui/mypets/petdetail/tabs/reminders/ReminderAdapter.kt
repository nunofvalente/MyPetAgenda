package com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.reminders

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nunovalente.android.mypetagenda.databinding.AdapterReminderLayoutBinding
import com.nunovalente.android.mypetagenda.models.Reminder
import com.nunovalente.android.mypetagenda.models.getRecurringDays


class ReminderAdapter(private var listener: OnToggleReminderListener): RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    private var reminderList: List<Reminder> = arrayListOf()

    class ReminderViewHolder(val binding: AdapterReminderLayoutBinding): RecyclerView.ViewHolder(
        binding.root
    ) {

        fun bind(reminder: Reminder, listener: OnToggleReminderListener) {
            if(reminder.isRecurring) {
                binding.adapterReminderDays.text = reminder.getRecurringDays()
            } else {
                binding.adapterReminderDays.text = reminder.date
            }

            binding.switchReminder.isChecked = reminder.isStarted

            binding.switchReminder.setOnCheckedChangeListener { _, _ ->
                listener.onToggle(reminder)
            }

            binding.cardViewPetReminder.setOnLongClickListener {
                listener.onLongClick(reminder)
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding = AdapterReminderLayoutBinding.inflate(inflater, parent, false)

        return ReminderViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = reminderList[position]
        holder.binding.reminder = reminder

        holder.bind(reminder, listener)
    }

    override fun getItemCount(): Int {
        return reminderList.size
    }

    override fun onViewRecycled(holder: ReminderViewHolder) {
        super.onViewRecycled(holder)
        holder.binding.switchReminder.setOnCheckedChangeListener(null)
    }

    fun setValue(reminders: List<Reminder>) {
        reminderList = reminders
        notifyDataSetChanged()
    }
}

interface OnToggleReminderListener {
    fun onToggle(reminder: Reminder?)
    fun onLongClick(reminder: Reminder?)
}