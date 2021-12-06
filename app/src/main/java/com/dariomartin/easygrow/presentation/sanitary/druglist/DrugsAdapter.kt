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
            LayoutInflater.from(parent.context).inflate(R.layout.drug_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DrugsViewHolder, position: Int) {
        val drug = drugs[position]
        holder.itemView.setOnClickListener { listener(drug) }
        holder.bind(drug)
    }

    override fun getItemCount() = drugs.size

    fun setDrugs(drugs: List<Drug>) {
        this.drugs.clear()
        this.drugs.addAll(drugs.sortedWith(compareBy({ it.pharmacy }, { it.name })))
        notifyDataSetChanged()
    }

    fun getItem(position: Int) = drugs[position]

    fun removeAt(position: Int) {
        drugs.removeAt(position)
        notifyItemRemoved(position)
    }

}

class DrugsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = DrugItemBinding.bind(view)

    fun bind(drug: Drug) {
        binding.name.text = drug.name
        binding.pharmacy.text = drug.pharmacy
        binding.concentration.text = drug.getConcentrationString()
    }

}
