package com.dariomartin.easygrow.presentation.patient.dose

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.data.model.Administration
import com.dariomartin.easygrow.data.model.Treatment
import com.dariomartin.easygrow.data.model.User
import com.dariomartin.easygrow.databinding.TreatmentAdministrationItemBinding
import com.dariomartin.easygrow.databinding.TreatmentHeaderBinding
import com.dariomartin.easygrow.utils.Utils
import com.dariomartin.easygrow.utils.Utils.dateToString

class DosesAdapter(private val onHeaderClick: () -> Any) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val HEADER: Int = 0
        private const val DOSE: Int = 1
    }

    var type: User.Type = User.Type.PATIENT
        set(value) {
            field = value
            notifyItemChanged(0)
        }

    var treatment: Treatment? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var administrations: List<Administration> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER -> HeaderViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.treatment_header, parent, false)
            )
            else -> DoseViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.treatment_administration_item, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> treatment?.let {
                if (type == User.Type.SANITARY) {
                    holder.itemView.setOnClickListener { onHeaderClick() }
                }
                holder.bind(type, it)
            }
            is DoseViewHolder -> holder.bind(administrations[position - 1])
        }
    }

    override fun getItemCount() = administrations.size + 1

    override fun getItemViewType(position: Int) = if (position == 0) HEADER else DOSE

}


class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = TreatmentHeaderBinding.bind(view)

    fun bind(type: User.Type, treatment: Treatment) {
        binding.treatmentName.text = treatment.drug
        binding.dose.text = itemView.context.getString(
            R.string.dose_item_dose,
            treatment.dose.number.toFloat(),
            Utils.getMeasureUnitAbbreviation(treatment.dose.unit)
        )
        binding.remainingPens.text = itemView.context.getString(
            R.string.dose_item_remaining_pens,
            treatment.pens.size
        )
        binding.arrow.visibility = if (type == User.Type.SANITARY) View.VISIBLE else View.GONE
    }

}

class DoseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = TreatmentAdministrationItemBinding.bind(view)

    fun bind(administration: Administration) {
        binding.date.text = dateToString("dd MMM yyyy", administration.date.timeInMillis)
        binding.hour.text = dateToString("HH:mm", administration.date.timeInMillis)
        binding.part.text = administration.bodyPart.getFullName(itemView.context)
    }
}
