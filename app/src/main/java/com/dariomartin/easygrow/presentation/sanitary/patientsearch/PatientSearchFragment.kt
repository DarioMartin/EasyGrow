package com.dariomartin.easygrow.presentation.sanitary.patientsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dariomartin.easygrow.databinding.PatientSearchFragmentBinding
import com.dariomartin.easygrow.presentation.sanitary.tabs.SanitaryTabsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientSearchFragment : Fragment() {

    companion object {
        fun newInstance() = SanitaryTabsFragment()
    }

    private var _binding: PatientSearchFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PatientSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[PatientSearchViewModel::class.java]
        _binding = PatientSearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}