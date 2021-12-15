package com.dariomartin.easygrow.presentation.sanitary.patients

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.databinding.FragmentPatientsTabBinding
import com.dariomartin.easygrow.presentation.sanitary.tabs.TabItemListener
import com.dariomartin.easygrow.presentation.utils.BaseFragment
import com.dariomartin.easygrow.presentation.utils.SwipeToDeleteCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientsTabFragment : BaseFragment<FragmentPatientsTabBinding, PatientsTabViewModel>() {

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
                val patient = adapter.getItem(viewHolder.adapterPosition)
                showRemovePatientDialog(viewHolder, patient)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

    }

    private fun listPatients(patients: List<Patient>) {
        binding.emptyMessage.layout.isVisible = false
        adapter.setPatients(patients)
    }

    private fun showEmptyMessage() {
        binding.emptyMessage.layout.isVisible = true
        adapter.setPatients(listOf())
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPatientsTabBinding {
        return FragmentPatientsTabBinding.inflate(inflater, container, false)
    }

    override fun provideViewModel(): PatientsTabViewModel {
        return ViewModelProvider(this)[PatientsTabViewModel::class.java]
    }

    private fun showRemovePatientDialog(
        viewHolder: RecyclerView.ViewHolder,
        patient: Patient
    ) {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }

        builder?.setTitle(R.string.dialog_remove_patient_title)
            ?.setMessage(getString(R.string.dialog_remove_patient_body, patient.name))
            ?.setPositiveButton(R.string.accept) { _, _ ->
                adapter.removeAt(viewHolder.adapterPosition)
                viewModel.removePatientFromDoctor(patient.id)
            }
            ?.setNegativeButton(R.string.cancel) { dialog, _ ->
                adapter.notifyItemChanged(viewHolder.adapterPosition)
                dialog.dismiss()
            }
            ?.setCancelable(false)
        val dialog: AlertDialog? = builder?.create()

        dialog?.show()
    }
}