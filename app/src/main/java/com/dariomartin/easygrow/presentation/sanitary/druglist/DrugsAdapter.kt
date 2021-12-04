package com.dariomartin.easygrow.presentation.sanitary.druglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.data.model.Drug
import com.dariomartin.easygrow.databinding.PatientItemBinding

class DrugsAdapter(private var drugs: List<Drug> = listOf()) :
    RecyclerView.Adapter<DrugsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrugsViewHolder {
        return DrugsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.patient_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DrugsViewHolder, position: Int) {
        holder.bind(drugs[position])
    }

    override fun getItemCount() = drugs.size

    fun setDrugs(drugs: List<Drug>) {
        this.drugs = drugs
        notifyDataSetChanged()
    }
}

class DrugsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = PatientItemBinding.bind(view)

    fun bind(drug: Drug) {
        binding.name.text = drug.name
    }
}
