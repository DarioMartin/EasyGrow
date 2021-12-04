package com.dariomartin.easygrow.presentation.sanitary

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dariomartin.easygrow.presentation.sanitary.druglist.DrugsTabFragment
import com.dariomartin.easygrow.presentation.sanitary.patients.PatientsTabFragment

class TabsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) PatientsTabFragment.newInstance()
        else DrugsTabFragment.newInstance()
    }
}