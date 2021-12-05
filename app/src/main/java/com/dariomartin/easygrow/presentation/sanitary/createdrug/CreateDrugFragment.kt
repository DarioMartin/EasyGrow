package com.dariomartin.easygrow.presentation.sanitary.createdrug

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.databinding.CreateDrugFragmentBinding
import com.dariomartin.easygrow.presentation.utils.BaseFragment
import com.dariomartin.easygrow.utils.Extensions.afterTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateDrugFragment : BaseFragment<CreateDrugFragmentBinding, CreateDrugViewModel>() {

    private var form: DrugForm = DrugForm()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.drugDataTitle.text = getString(R.string.create_new_drug)
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

        binding.submitButton.setOnClickListener {
            hideErrors()
            submitForm()
        }
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