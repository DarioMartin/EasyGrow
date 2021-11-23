package com.dariomartin.easygrow.ui.patient.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.databinding.FragmentProfileUpdateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileUpdateFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileUpdateBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileViewModel =
            ViewModelProvider(this)[ProfileViewModel::class.java]

        _binding = FragmentProfileUpdateBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.patient
            .observe(
                viewLifecycleOwner,
                { patient -> patient?.let { paintPatient(patient) } })

        binding.submitButton.setOnClickListener { submitForm() }
    }

    private fun submitForm() {
        val patientForm = PatientForm(
            name = binding.name.text.toString(),
            surname = binding.surname.text.toString(),
            height = binding.height.text.toString().toInt(),
            weight = binding.weight.text.toString().toFloat(),
        )

        profileViewModel.updatePatient(patientForm)
    }

    private fun paintPatient(patient: Patient) {
        binding.name.setText(patient.name)
        binding.surname.setText(patient.surname)
        binding.height.setText(patient.height.toString())
        binding.weight.setText(patient.weight.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}