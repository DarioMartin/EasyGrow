package com.dariomartin.easygrow.presentation.sanitary.treatmentupdate

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dariomartin.easygrow.data.model.Drug
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.databinding.TreatmentUpdateFragmentBinding
import com.dariomartin.easygrow.presentation.utils.BaseFragment
import com.dariomartin.easygrow.utils.Extensions.afterTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TreatmentUpdateFragment :
    BaseFragment<TreatmentUpdateFragmentBinding, TreatmentUpdateViewModel>() {

    private val args: TreatmentUpdateFragmentArgs by navArgs()
    private var form: TreatmentForm? = null
    private var patient: Patient? = null
    private var drugs: List<Drug> = listOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPatient(args.patientId).observe(viewLifecycleOwner, {
            it?.let { patient ->
                this.patient = patient
                if (form == null) {
                    form = TreatmentForm(
                        drug = patient.treatment?.drug,
                        dose = patient.treatment?.dose?.number?.toFloat() ?: 0F
                    )
                }
                updateTreatment()
            }
        })

        viewModel.successfulUpdate.observe(viewLifecycleOwner, { successfulUpdate ->
            if (successfulUpdate) {
                findNavController().popBackStack()
            }
        })

        viewModel.getDrugs().observe(viewLifecycleOwner, {
            drugs = it
            setupAutocompleteField(it)
        })

        binding.drugAutocomplete.afterTextChanged {
            hideErrors()
            form?.drug = it
        }

        binding.dose.afterTextChanged {
            hideErrors()
            form?.dose = try {
                it.toFloat()
            } catch (e: NumberFormatException) {
                0F
            }
        }

        binding.addPen.setOnClickListener {
            val drug = drugs.find { it.name == form?.drug }
            if (drug == null) {
                showErrors()
            } else {
                viewModel.addPen(args.patientId, drug)
            }
        }

        binding.submitButton.setOnClickListener {
            hideErrors()
            submitForm()
        }
    }

    private fun setupAutocompleteField(drugs: List<Drug>) {
        ArrayAdapter(
            requireContext(),
            R.layout.simple_list_item_1,
            drugs.map { it.name }
        ).also { adapter ->
            binding.drugAutocomplete.setAdapter(adapter)
        }

        binding.drugAutocomplete.setOnItemClickListener { parent, _, position, _ ->
            val drugSelected = parent.adapter.getItem(position) as String
            form?.drug = drugSelected
            patient?.treatment?.drug = drugSelected
        }
    }

    private fun updateTreatment() {
        binding.drugAutocomplete.setText(form?.drug)
        binding.dose.setText(form?.dose.toString())
        binding.availablePens.text = requireContext().getString(
            com.dariomartin.easygrow.R.string.dose_item_remaining_pens,
            patient?.treatment?.pens?.size
        )
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): TreatmentUpdateFragmentBinding {
        return TreatmentUpdateFragmentBinding.inflate(inflater, container, false)
    }

    override fun provideViewModel(): TreatmentUpdateViewModel {
        return ViewModelProvider(this)[TreatmentUpdateViewModel::class.java]
    }

    private fun hideErrors() {
        binding.drugInputLayout.error = null
        binding.doseInputLayout.error = null
    }

    private fun showErrors() {
        if (form?.isValidDrug(drugs) == true) binding.drugInputLayout.error =
            requireContext().getString(com.dariomartin.easygrow.R.string.invalid_value)
        if (form?.isValidDose() == true) binding.doseInputLayout.error =
            requireContext().getString(com.dariomartin.easygrow.R.string.invalid_value)
    }


    private fun submitForm() {
        form?.let {
            if (form?.isValid(drugs) == true && form != null) {
                viewModel.updateTreatment(args.patientId, it)
            } else {
                showErrors()
            }
        }
    }

}