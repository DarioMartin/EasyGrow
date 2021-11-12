package com.dariomartin.easygrow.ui.patient

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.databinding.FragmentProfileBinding
import com.dariomartin.easygrow.model.Patient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private val dosesAdapter: DosesAdapter = DosesAdapter()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileViewModel =
            ViewModelProvider(this)[ProfileViewModel::class.java]

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.getPatient()
            .observe(
                viewLifecycleOwner,
                { patient -> patient?.let { paintPatient(patient) } ?: onPatientError() })

        profileViewModel.getDoses()
            .observe(
                viewLifecycleOwner,
                { doses -> dosesAdapter.administrations = doses })

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = dosesAdapter
        }

    }

    private fun onPatientError() {

    }

    @SuppressLint("SetTextI18n")
    private fun paintPatient(patient: Patient) {
        binding.header.name.text =
            getString(R.string.full_name_format, patient.name, patient.surname)
        binding.header.height.text = getString(R.string.height_cm_format, patient.height)
        binding.header.years.text = getString(R.string.years_format, patient.getAge())
        binding.header.weight.text = getString(R.string.weight_format, patient.weight)

        Glide.with(requireContext()).load(patient.photo).circleCrop()
            .into(binding.header.patientPicture)

        dosesAdapter.treatment = patient.treatment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}