package com.dariomartin.easygrow.presentation.sanitary.patientsearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.databinding.PatientSearchItemBinding

class SearchPatientsAdapter(
    private var patients: MutableList<Patient> = mutableListOf(),
    private val listener: (Patient) -> Unit = {}
) :
    RecyclerView.Adapter<SearchPatientsAdapter.PatientViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        return PatientViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.patient_search_item, parent, false)
        )
    }

    private var assigned: List<Patient> = listOf()
    private var query = ""
    private fun filteredPatients() = patients.filter {
        (it.name.contains(query, true) || it.surname.contains(query, true))
                && !assigned.contains(it)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = filteredPatients()[position]
        holder.itemView.setOnClickListener { listener(patient) }
        holder.bind(patient)
    }

    override fun getItemCount() = filteredPatients().size

    fun setPatients(patients: List<Patient>) {
        this.patients.clear()
        this.patients.addAll(patients.sortedBy { it.name })
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        this.query = query
        notifyDataSetChanged()
    }

    fun setAssigned(patients: MutableList<Patient>) {
        assigned = patients
        notifyDataSetChanged()
    }

    inner class PatientViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = PatientSearchItemBinding.bind(view)

        fun bind(patient: Patient) {
            binding.name.text =
                itemView.context.getString(R.string.name_surname, patient.name, patient.surname)
            binding.age.text = itemView.context.getString(R.string.years_format, patient.getAge())
        }
    }
}


