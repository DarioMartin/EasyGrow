package com.dariomartin.easygrow.ui.sanitary.patients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.databinding.FragmentSanitaryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientsTabFragment : Fragment() {

    companion object {
        fun newInstance(): Fragment {
            return PatientsTabFragment()
        }
    }

    private lateinit var pageViewModel: PatientsTabViewModel
    private var _binding: FragmentSanitaryBinding? = null
    private val binding get() = _binding!!

    private val adapter = PatientsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this)[PatientsTabViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSanitaryBinding.inflate(inflater, container, false)
        val root = binding.root

        setupRecyclerView()

        pageViewModel.patients.observe(viewLifecycleOwner, { patients ->
            if (patients.isNullOrEmpty()) {
                showEmptyMessage()
            } else {
                listPatients(patients)
            }
        })
        return root
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


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}