package com.dariomartin.easygrow.presentation.sanitary.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.data.model.Drug
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.databinding.SanitaryTabsFragmentBinding
import com.dariomartin.easygrow.presentation.sanitary.TabsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


interface TabItemListener {
    fun onTabItemClicked(item: Any)
}

@AndroidEntryPoint
class SanitaryTabsFragment : Fragment(), TabItemListener {

    companion object {
        fun newInstance() = SanitaryTabsFragment()

        private val TAB_TITLES = arrayOf(
            R.string.patients_tab,
            R.string.drugs_tab
        )
    }


    private var _binding: SanitaryTabsFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SanitaryTabsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[SanitaryTabsViewModel::class.java]
        _binding = SanitaryTabsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTabs()
    }

    private fun setUpTabs() {

        val sectionsPagerAdapter = TabsPagerAdapter(this, this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = getString(TAB_TITLES[position])
        }.attach()

        binding.fab.setOnClickListener {
            if (viewPager.currentItem == 0) {
                goToSearchPatient()
            }
        }
    }

    private fun goToDrugDetails(selection: Drug) {

    }

    private fun goToPatientDetails(patient: Patient) {
        val action =
            SanitaryTabsFragmentDirections.actionSanitaryTabsFragmentToProfileFragment(patient.id)
        findNavController().navigate(action)
    }

    private fun goToSearchPatient() {
        val action =
            SanitaryTabsFragmentDirections.actionSanitaryTabsFragmentToPatientSearchFragment()
        findNavController().navigate(action)
    }

    override fun onTabItemClicked(item: Any) {
        when (item) {
            is Patient -> goToPatientDetails(item)
            is Drug -> goToDrugDetails(item)
        }
    }

}