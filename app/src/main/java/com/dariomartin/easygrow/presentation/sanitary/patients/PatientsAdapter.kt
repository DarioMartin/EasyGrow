package com.dariomartin.easygrow.presentation.sanitary.patients

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.databinding.PatientItemBinding

class PatientsAdapter(private var patients: List<Patient> = listOf(), private val listener: (Patient) -> Unit) :
    RecyclerView.Adapter<PatientViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        return PatientViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.patient_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = patients[position]
        holder.itemView.setOnClickListener { listener(patient) }
        holder.bind(patient)
    }

    override fun getItemCount() = patients.size

    fun setPatients(patients: List<Patient>) {
        this.patients = patients
        notifyDataSetChanged()
    }
}

class PatientViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = PatientItemBinding.bind(view)

    fun bind(patient: Patient) {
        binding.name.text = patient.name
        binding.treatmentName.text = patient.treatment?.drug?.name
        binding.age.text = itemView.context.getString(R.string.years_format, patient.getAge())
        Glide.with(itemView.context)
            .load(patient.photo)
            .error(R.drawable.ic_kid_placeholder)
            .circleCrop()
            .into(binding.image)
    }
}
