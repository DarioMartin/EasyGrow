package com.dariomartin.easygrow.ui.sanitary

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.ui.sanitary.drugs.DrugsTabFragment
import com.dariomartin.easygrow.ui.sanitary.patients.PatientsTabFragment

private val TAB_TITLES = arrayOf(
    R.string.patients_tab,
    R.string.drugs_tab
)

class TabsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return if (position == 0) PatientsTabFragment.newInstance()
        else DrugsTabFragment.newInstance()
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }
}