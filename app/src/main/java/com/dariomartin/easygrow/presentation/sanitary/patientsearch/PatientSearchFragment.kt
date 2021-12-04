package com.dariomartin.easygrow.presentation.sanitary.patientsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.databinding.PatientSearchFragmentBinding
import com.dariomartin.easygrow.presentation.sanitary.patients.PatientsAdapter
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

    private val adapter = SearchPatientsAdapter { patient ->
        viewModel.assignPatient(patient)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[PatientSearchViewModel::class.java]
        _binding = PatientSearchFragmentBinding.inflate(inflater, container, false)

        setupRecyclerView()

        viewModel.getPatients().observe(viewLifecycleOwner, { patients ->
            if (patients.isNullOrEmpty()) {
                showEmptyMessage()
            } else {
                listPatients(patients)
            }
        })

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        binding.recyclerView.adapter = adapter
    }

    private fun listPatients(patients: List<Patient>) {
        adapter.setPatients(patients)
    }

    private fun showEmptyMessage() {
        adapter.setPatients(listOf())
    }
}