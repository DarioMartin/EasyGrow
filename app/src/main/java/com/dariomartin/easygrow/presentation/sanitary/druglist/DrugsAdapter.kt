package com.dariomartin.easygrow.presentation.sanitary.druglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.data.model.Drug
import com.dariomartin.easygrow.databinding.DrugItemBinding

class DrugsAdapter(
    private var drugs: MutableList<Drug> = mutableListOf(),
    private val listener: (Drug) -> Unit = {}
) :
    RecyclerView.Adapter<DrugsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrugsViewHolder {
        return DrugsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.patient_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DrugsViewHolder, position: Int) {
        val drug = drugs[position]
        holder.itemView.setOnClickListener { listener(drug) }
        holder.bind(drugs[position])
    }

    override fun getItemCount() = drugs.size

    fun setDrugs(drugs: List<Drug>) {
        this.drugs.clear()
        this.drugs.addAll(drugs.sortedBy { it.name })
        notifyDataSetChanged()
    }

    fun getItem(position: Int) = drugs[position]

}

class DrugsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = DrugItemBinding.bind(view)

    fun bind(drug: Drug) {
        binding.name.text = drug.name
    }
}
