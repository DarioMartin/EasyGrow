package com.dariomartin.easygrow.presentation.patient.profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.data.model.User
import com.dariomartin.easygrow.databinding.FragmentProfileBinding
import com.dariomartin.easygrow.presentation.patient.dose.DosesAdapter
import com.dariomartin.easygrow.presentation.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    private val dosesAdapter: DosesAdapter = DosesAdapter {
        goToUpdateTreatment()
    }

    private val args: ProfileFragmentArgs by navArgs()
    private var patientId: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        patientId = args.patientId

        viewModel.userType.observe(viewLifecycleOwner, {
            it?.let {
                dosesAdapter.type = it
                if (it == User.Type.SANITARY) {
                    setHasOptionsMenu(true)
                }
            }
        })

        viewModel.getPatient(patientId).observe(
            viewLifecycleOwner,
            { patient -> patient?.let { paintPatient(patient) } ?: onPatientError() })

        viewModel.getAdministrations(args.patientId).observe(
            viewLifecycleOwner,
            { doses ->
                dosesAdapter.administrations = doses
            })

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
        patientId?.let {
            val action =
                ProfileFragmentDirections.actionProfileFragmentToProfileUpdateFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun onPatientError() {

    }

    @SuppressLint("SetTextI18n")
    private fun paintPatient(patient: Patient) {
        patientId = patient.id
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
            //TODO not showing by now
            //showCompleteDataDialog()
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
            ?.setCancelable(false)

        val dialog: AlertDialog? = builder?.create()

        dialog?.show()
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun provideViewModel(): ProfileViewModel {
        return ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    private fun goToUpdateTreatment() {
        patientId?.let {
            val action =
                ProfileFragmentDirections.actionProfileFragmentToTreatmentUpdateFragment(it)
            findNavController().navigate(action)
        }
    }

}

