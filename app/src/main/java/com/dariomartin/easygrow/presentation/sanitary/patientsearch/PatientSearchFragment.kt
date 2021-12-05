package com.dariomartin.easygrow.presentation.sanitary.patientsearch

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.data.model.Patient
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

    private val adapter = SearchPatientsAdapter { patient ->
        viewModel.assignPatient(patient)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[PatientSearchViewModel::class.java]
        _binding = PatientSearchFragmentBinding.inflate(inflater, container, false)

        setupRecyclerView()

        viewModel.getDoctorPatients().observe(viewLifecycleOwner, { patients ->
            adapter.setAssigned(patients ?: mutableListOf())
        })

        viewModel.getAllPatients().observe(viewLifecycleOwner, { patients ->
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
        searchView.setOnClickListener { view -> }
    }

}