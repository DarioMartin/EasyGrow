package com.dariomartin.easygrow.presentation.patient.profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.databinding.FragmentProfileBinding
import com.dariomartin.easygrow.presentation.patient.DosesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private val dosesAdapter: DosesAdapter = DosesAdapter()

    private val binding get() = _binding!!

    val args: ProfileFragmentArgs by navArgs()

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

        setHasOptionsMenu(true)

        profileViewModel.getPatient(args.patientId).observe(
            viewLifecycleOwner,
            { patient -> patient?.let { paintPatient(patient) } ?: onPatientError() })

        profileViewModel.administrations
            .observe(
                viewLifecycleOwner,
                { doses -> dosesAdapter.administrations = doses })

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = dosesAdapter
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.patient_profile_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit -> {
                goToDetails()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToDetails() {
        val action = ProfileFragmentDirections.actionNavigationProfileToNavigationProfileUpdate()
        findNavController().navigate(action)
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

        Glide.with(requireContext())
            .load(patient.photo)
            .error(R.drawable.ic_kid_placeholder)
            .circleCrop()
            .into(binding.header.patientPicture)

        dosesAdapter.treatment = patient.treatment

        if (patient.missingData()) {
            showCompleteDataDialog()
        }
    }

    private fun showCompleteDataDialog() {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }

        builder?.setTitle(R.string.dialog_missing_patient_data_title)
            ?.setMessage(R.string.dialog_missing_patient_data_body)
            ?.setPositiveButton(R.string.accept) { _, _ -> goToDetails() }
            ?.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
        val dialog: AlertDialog? = builder?.create()

        dialog?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

