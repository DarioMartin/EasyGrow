package com.dariomartin.easygrow.presentation.patient.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.data.model.User
import com.dariomartin.easygrow.databinding.FragmentProfileUpdateBinding
import com.dariomartin.easygrow.presentation.utils.BaseFragment
import com.dariomartin.easygrow.presentation.utils.DatePickerFragment
import com.dariomartin.easygrow.utils.Extensions.afterTextChanged
import com.dariomartin.easygrow.utils.Utils.dateToString
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ProfileUpdateFragment : BaseFragment<FragmentProfileUpdateBinding, ProfileViewModel>() {

    private val args: ProfileUpdateFragmentArgs by navArgs()
    private var form: PatientForm = PatientForm()
    private var type: User.Type = User.Type.SANITARY

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPatient(args.patientId).observe(
            viewLifecycleOwner,
            { patient ->
                form.name = patient.name
                form.surname = patient.surname
                form.height = patient.height
                form.weight = patient.weight
                form.birthday = patient.birthday
                updatePatient()
            })

        viewModel.successfulUpdate.observe(viewLifecycleOwner, { successfulUpdate ->
            if (successfulUpdate) {
                findNavController().popBackStack()
            }
        })

        viewModel.userType.observe(viewLifecycleOwner, {
            it?.let {
                type = it
                updatePatient()
            }
        })

        binding.name.afterTextChanged {
            hideErrors()
            form.name = it
        }
        binding.surname.afterTextChanged {
            hideErrors()
            form.surname = it
        }

        binding.weight.afterTextChanged {
            hideErrors()
            form.weight = try {
                it.toFloat()
            } catch (e: NumberFormatException) {
                0F
            }
        }

        binding.height.afterTextChanged {
            hideErrors()
            form.height = try {
                it.toInt()
            } catch (e: java.lang.NumberFormatException) {
                0
            }
        }
        binding.birthday.setOnClickListener {
            hideErrors()
            showDatePicker()
        }
        binding.submitButton.setOnClickListener {
            hideErrors()
            submitForm()
        }
    }

    private fun showDatePicker() {
        val newFragment =
            DatePickerFragment.newInstance(form.birthday) { _, year, month, day ->
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.DAY_OF_MONTH, day)
                calendar.set(Calendar.MONDAY, month)
                calendar.set(Calendar.YEAR, year)
                form.birthday = calendar
                updatePatient()
            }
        newFragment.show(requireActivity().supportFragmentManager, "datePicker")
    }

    private fun submitForm() {
        if (form.isValid(type)) {
            viewModel.updatePatient(args.patientId, form)
        } else {
            showErrors()
        }
    }

    private fun showErrors() {
        if (!form.isValidName()) binding.nameInputLayout.error =
            requireContext().getString(R.string.invalid_value)
        if (!form.isValidSurname()) binding.surnameInputLayout.error =
            requireContext().getString(R.string.invalid_value)
        if (!form.isValidWeight(type)) binding.weightInputLayout.error =
            requireContext().getString(R.string.invalid_value)
        if (!form.isValidHeight(type)) binding.heightInputLayout.error =
            requireContext().getString(R.string.invalid_value)
        if (!form.isValidBirthday()) binding.birthdayInputLayout.error =
            requireContext().getString(R.string.invalid_value)
    }

    private fun hideErrors() {
        binding.nameInputLayout.error = null
        binding.surnameInputLayout.error = null
        binding.weightInputLayout.error = null
        binding.heightInputLayout.error = null
        binding.birthdayInputLayout.error = null
    }

    private fun updatePatient() {
        binding.weightInputLayout.isEnabled = type == User.Type.SANITARY
        binding.heightInputLayout.isEnabled = type == User.Type.SANITARY

        binding.name.setText(form.name)
        binding.surname.setText(form.surname)
        binding.height.setText(form.height.toString())
        binding.weight.setText(form.weight.toString())
        form.birthday?.let {
            binding.birthday.setText(dateToString("dd/MM/yyyy", it.timeInMillis))
        }

        Glide.with(requireContext())
            .load(form.image)
            .error(R.drawable.ic_kid_placeholder)
            .circleCrop()
            .into(binding.patientPicture)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileUpdateBinding {
        return FragmentProfileUpdateBinding.inflate(inflater, container, false)
    }

    override fun provideViewModel(): ProfileViewModel {
        return ViewModelProvider(this)[ProfileViewModel::class.java]
    }
}

