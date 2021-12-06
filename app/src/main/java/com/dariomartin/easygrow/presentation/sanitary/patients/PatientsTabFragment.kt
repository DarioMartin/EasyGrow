package com.dariomartin.easygrow.presentation.sanitary.patients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.databinding.FragmentSanitaryBinding
import com.dariomartin.easygrow.presentation.sanitary.tabs.TabItemListener
import com.dariomartin.easygrow.presentation.utils.BaseFragment
import com.dariomartin.easygrow.presentation.utils.SwipeToDeleteCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientsTabFragment : BaseFragment<FragmentSanitaryBinding, PatientsTabViewModel>() {

    companion object {
        fun newInstance(listener: TabItemListener?): Fragment {
            val fragment = PatientsTabFragment()
            fragment.listener = listener
            return fragment
        }
    }

    private var listener: TabItemListener? = null
    private val adapter = PatientsAdapter { patient -> listener?.onTabItemClicked(patient) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewModel.getPatients().observe(viewLifecycleOwner, { patients ->
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

        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding.recyclerView.adapter as PatientsAdapter
                val patient = adapter.getItem(viewHolder.adapterPosition)
                viewModel.removePatientFromDoctor(patient.id)
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

    }

    private fun listPatients(patients: List<Patient>) {
        adapter.setPatients(patients)
    }

    private fun showEmptyMessage() {
        adapter.setPatients(listOf())
    }


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSanitaryBinding {
        return FragmentSanitaryBinding.inflate(inflater, container, false)
    }

    override fun provideViewModel(): PatientsTabViewModel {
        return ViewModelProvider(this)[PatientsTabViewModel::class.java]
    }
}