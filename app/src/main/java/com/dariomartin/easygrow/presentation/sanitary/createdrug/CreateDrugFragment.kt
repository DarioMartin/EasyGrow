package com.dariomartin.easygrow.presentation.sanitary.createdrug

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.databinding.CreateDrugFragmentBinding
import com.dariomartin.easygrow.presentation.utils.BaseFragment
import com.dariomartin.easygrow.utils.Extensions.afterTextChanged
import com.dariomartin.easygrow.utils.Extensions.niceDecimalNumber
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateDrugFragment : BaseFragment<CreateDrugFragmentBinding, CreateDrugViewModel>() {

    private val args: CreateDrugFragmentArgs by navArgs()
    private var form: DrugForm = DrugForm()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val drugId = args.drugId

        binding.drugDataTitle.text =
            getString(if (drugId == null) R.string.create_new_drug else R.string.update_drug)

        drugId?.let {
            viewModel.getDrug(it).observe(
                viewLifecycleOwner,
                { drug ->
                    form.apply {
                        name = drug.name
                        pharmacy = drug.pharmacy
                        drugMass = drug.concentration.mass.number.toFloat()
                        drugVolume = drug.concentration.volume.number.toFloat()
                        cartridgeVolume = drug.cartridgeVolume.number.toFloat()
                        url = drug.url
                    }
                    updateDrug()
                })
        }

        viewModel.successfulSaving.observe(viewLifecycleOwner, { successfulUpdate ->
            if (successfulUpdate) {
                findNavController().popBackStack()
            }
        })

        binding.name.afterTextChanged {
            hideErrors()
            form.name = it
        }

        binding.pharmacy.afterTextChanged {
            hideErrors()
            form.pharmacy = it
        }

        binding.mass.afterTextChanged {
            hideErrors()
            form.drugMass = try {
                it.toFloat()
            } catch (e: java.lang.NumberFormatException) {
                0F
            }
        }

        binding.volume.afterTextChanged {
            hideErrors()
            form.drugVolume = try {
                it.toFloat()
            } catch (e: java.lang.NumberFormatException) {
                0F
            }
        }

        binding.cartridgeVolume.afterTextChanged {
            hideErrors()
            form.cartridgeVolume = try {
                it.toFloat()
            } catch (e: java.lang.NumberFormatException) {
                0F
            }
        }

        binding.url.afterTextChanged {
            hideErrors()
            form.url = it
        }

        binding.submitButton.text =
            getString(if (drugId == null) R.string.create else R.string.update)
        binding.submitButton.setOnClickListener {
            hideErrors()
            submitForm()
        }
    }

    private fun updateDrug() {
        binding.name.setText(form.name)
        binding.pharmacy.setText(form.pharmacy)
        binding.mass.setText(form.drugMass.toString())
        binding.volume.setText(form.drugVolume.toString())
        binding.cartridgeVolume.setText(form.cartridgeVolume.toString())
        binding.url.setText(form.url)
    }

    private fun submitForm() {
        if (form.isValid()) {
            viewModel.saveDrug(form)
        } else {
            showErrors()
        }
    }

    private fun showErrors() {
        if (!form.isValidName()) binding.nameInputLayout.error =
            requireContext().getString(R.string.invalid_value)
        if (!form.isValidPharmacy()) binding.pharmacyInputLayout.error =
            requireContext().getString(R.string.invalid_value)
        if (!form.isValidDrugMass()) binding.massInputLayout.error =
            requireContext().getString(R.string.invalid_value)
        if (!form.isValidDrugVolume()) binding.volumeInputLayout.error =
            requireContext().getString(R.string.invalid_value)
        if (!form.isValidCartridgeVolume()) binding.cartridgeVolumeInputLayout.error =
            requireContext().getString(R.string.invalid_value)
    }

    private fun hideErrors() {
        binding.nameInputLayout.error = null
        binding.pharmacyInputLayout.error = null
        binding.massInputLayout.error = null
        binding.volumeInputLayout.error = null
        binding.cartridgeVolumeInputLayout.error = null
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): CreateDrugFragmentBinding {
        return CreateDrugFragmentBinding.inflate(inflater, container, false)
    }

    override fun provideViewModel(): CreateDrugViewModel {
        return ViewModelProvider(this)[CreateDrugViewModel::class.java]
    }

}