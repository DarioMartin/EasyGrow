package com.dariomartin.easygrow.presentation.sanitary.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.dariomartin.easygrow.databinding.SanitaryTabsFragmentBinding
import com.dariomartin.easygrow.presentation.sanitary.TabsPagerAdapter
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SanitaryTabsFragment : Fragment() {

    companion object {
        fun newInstance() = SanitaryTabsFragment()
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
        val sectionsPagerAdapter =
            TabsPagerAdapter(requireContext(), requireActivity().supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }

}