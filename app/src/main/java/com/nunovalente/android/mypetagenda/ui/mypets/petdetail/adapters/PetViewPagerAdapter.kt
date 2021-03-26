package com.nunovalente.android.mypetagenda.ui.mypets.petdetail.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.notes.NotesFragment
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.profile.ProfileFragment
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.reminders.RemindersFragment
import javax.inject.Inject

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

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Reminders"
            1 -> "Notes"
            else -> "Profile"
        }
    }


}