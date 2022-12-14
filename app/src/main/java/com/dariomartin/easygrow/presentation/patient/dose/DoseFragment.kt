package com.dariomartin.easygrow.presentation.patient.dose

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.data.model.Administration
import com.dariomartin.easygrow.data.model.BodyPart
import com.dariomartin.easygrow.databinding.FragmentDoseBinding
import com.dariomartin.easygrow.presentation.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DoseFragment : BaseFragment<FragmentDoseBinding, DosesViewModel>() {

    private var remainingDoses: Int = 0
    private var lastAdministrations: List<Administration> = listOf()
    private var newAdministration: Administration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLastNAdministrations(3).observe(viewLifecycleOwner, { administrations ->
            lastAdministrations = administrations
            updateBodyMap()
        })

        viewModel.getPatient().observe(viewLifecycleOwner, {
            viewModel.calculateDoses()
        })

        viewModel.penDoses.observe(viewLifecycleOwner, { penDoses ->
            updatePen(penDoses)
        })

        binding.body.armL.setOnClickListener {
            if (remainingDoses > 0) newAdministration(BodyPart.ARM_L)
        }
        binding.body.armR.setOnClickListener {
            if (remainingDoses > 0) newAdministration(BodyPart.ARM_R)
        }
        binding.body.absL.setOnClickListener {
            if (remainingDoses > 0) newAdministration(BodyPart.ABS_L)
        }
        binding.body.absR.setOnClickListener {
            if (remainingDoses > 0) newAdministration(BodyPart.ABS_R)
        }
        binding.body.legL.setOnClickListener {
            if (remainingDoses > 0) newAdministration(BodyPart.LEG_L)
        }
        binding.body.legR.setOnClickListener {
            if (remainingDoses > 0) newAdministration(BodyPart.LEG_R)
        }

        binding.header.loadButton.setOnClickListener {
            viewModel.useNewPen()
        }
    }

    private fun updatePen(penDoses: Pair<Int, Int>) {
        val totalDoses = penDoses.first
        remainingDoses = penDoses.second

        when {
            totalDoses == 0 -> {
                showNoPensDialog()
                binding.header.loadButton.visibility = View.GONE
            }
            remainingDoses == 0 -> {
                binding.header.loadButton.visibility = View.VISIBLE
                showNoDosesDialog()
            }
            else -> {
                binding.header.loadButton.visibility = View.GONE
                binding.header.pen.setDoses(totalDoses, remainingDoses)
                binding.header.remainingDoses.text =
                    getString(R.string.remaining_doses, penDoses.second)
            }
        }
    }

    private fun showNoDosesDialog() {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }

        builder?.setTitle(R.string.dialog_no_available_dosis_title)
            ?.setMessage(R.string.dialog_no_available_dosis_body)
            ?.setPositiveButton(R.string.accept) { _, _ ->
                viewModel.useNewPen()
            }
            ?.setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            ?.setCancelable(false)

        val dialog: AlertDialog? = builder?.create()

        dialog?.show()
    }

    private fun showNoPensDialog() {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }

        builder?.setTitle(R.string.dialog_no_pens_title)
            ?.setMessage(R.string.dialog_no_pens_body)
            ?.setPositiveButton(R.string.accept) { dialog, _ ->
                dialog.dismiss()
            }
            ?.setCancelable(false)

        val dialog: AlertDialog? = builder?.create()

        dialog?.show()
    }

    private fun updateBodyMap() {
        binding.body.armLCheck.visibility =
            if (getTotalAdministrations().any { it.bodyPart == BodyPart.ARM_L }) View.VISIBLE else View.GONE
        binding.body.armRCheck.visibility =
            if (getTotalAdministrations().any { it.bodyPart == BodyPart.ARM_R }) View.VISIBLE else View.GONE
        binding.body.absLCheck.visibility =
            if (getTotalAdministrations().any { it.bodyPart == BodyPart.ABS_L }) View.VISIBLE else View.GONE
        binding.body.absRCheck.visibility =
            if (getTotalAdministrations().any { it.bodyPart == BodyPart.ABS_R }) View.VISIBLE else View.GONE
        binding.body.legLCheck.visibility =
            if (getTotalAdministrations().any { it.bodyPart == BodyPart.LEG_L }) View.VISIBLE else View.GONE
        binding.body.legRCheck.visibility =
            if (getTotalAdministrations().any { it.bodyPart == BodyPart.LEG_R }) View.VISIBLE else View.GONE
    }

    private fun getTotalAdministrations(): List<Administration> {
        return newAdministration?.let { lastAdministrations + it } ?: lastAdministrations
    }

    private fun newAdministration(bodyPart: BodyPart) {
        if (lastAdministrations.none { it.bodyPart == bodyPart }) {
            val new = Administration(Calendar.getInstance(), bodyPart)
            newAdministration = if (new.bodyPart != newAdministration?.bodyPart) {
                new
            } else {
                null
            }
            viewModel.newAdministration(newAdministration)

            updateBodyMap()
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDoseBinding {
        return FragmentDoseBinding.inflate(inflater, container, false)
    }

    override fun provideViewModel(): DosesViewModel {
        return ViewModelProvider(this)[DosesViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.patient_dose_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.reminder -> {
                goToReminders()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToReminders() {
        val action = DoseFragmentDirections.actionNavigationDoseToReminderFragment()
        findNavController().navigate(action)
    }
}