package com.dariomartin.easygrow.presentation.sanitary.patientsearch

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.databinding.PatientSearchFragmentBinding
import com.dariomartin.easygrow.presentation.sanitary.tabs.SanitaryTabsFragment
import com.dariomartin.easygrow.presentation.utils.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PatientSearchFragment : BaseFragment<PatientSearchFragmentBinding, PatientSearchViewModel>() {

    companion object {
        fun newInstance() = SanitaryTabsFragment()
    }

    private val adapter = SearchPatientsAdapter { patient ->
        viewModel.assignPatient(patient)
        displaySnackBar(patient)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.getNotAssignedPatients().observe(viewLifecycleOwner, { patients ->
            if (patients.isNullOrEmpty()) {
                showEmptyMessage()
            } else {
                listPatients(patients)
            }
        })
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
        binding.emptyMessage.layout.isVisible = false
        adapter.setPatients(patients)
    }

    private fun showEmptyMessage() {
        binding.emptyMessage.layout.isVisible = true
        adapter.setPatients(listOf())
    }

    private fun displaySnackBar(patient: Patient) {
        val snackBar =
            Snackbar.make(
                binding.recyclerView,
                getString(R.string.patient_added_correctly, patient.name),
                Snackbar.LENGTH_LONG
            )
        snackBar.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.patient_search_menu, menu)
        val searchView = SearchView(requireContext())
        menu.findItem(R.id.action_search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter(newText)
                return false
            }
        })
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): PatientSearchFragmentBinding {
        return PatientSearchFragmentBinding.inflate(inflater, container, false)
    }

    override fun provideViewModel(): PatientSearchViewModel {
        return ViewModelProvider(this)[PatientSearchViewModel::class.java]
    }

}