package com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.reminders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentRemindersBinding

class RemindersFragment : Fragment() {


    private lateinit var binding: FragmentRemindersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reminders, container, false)


            return binding.root
        }

    companion object {
        @JvmStatic
        fun newInstance() = RemindersFragment()
    }
}