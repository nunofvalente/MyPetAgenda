package com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nunovalente.android.mypetagenda.R

class RemindersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reminders, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = RemindersFragment()
    }
}