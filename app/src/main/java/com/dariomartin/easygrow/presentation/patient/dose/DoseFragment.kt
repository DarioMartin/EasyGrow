package com.dariomartin.easygrow.presentation.patient.dose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.data.model.BodyPart
import com.dariomartin.easygrow.databinding.FragmentDoseBinding
import com.dariomartin.easygrow.presentation.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoseFragment : BaseFragment<FragmentDoseBinding, DosesViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.lastAdministrations.observe(viewLifecycleOwner, { dosesMap ->
            updateBodyMap(dosesMap)
        })

        viewModel.getPatient().observe(viewLifecycleOwner, { patient ->
            patient?.let {
                viewModel.calculateDoses(it)
            }
        })

        viewModel.penDoses.observe(viewLifecycleOwner, { penDoses ->
            updatePena(penDoses)
        })

        binding.body.armL.setOnClickListener {
            viewModel.newAdministration(BodyPart.ARM_L)
        }
        binding.body.armR.setOnClickListener {
            viewModel.newAdministration(BodyPart.ARM_R)
        }
        binding.body.absL.setOnClickListener {
            viewModel.newAdministration(BodyPart.ABS_L)
        }
        binding.body.absR.setOnClickListener {
            viewModel.newAdministration(BodyPart.ABS_R)
        }
        binding.body.legL.setOnClickListener {
            viewModel.newAdministration(BodyPart.LEG_L)
        }
        binding.body.legR.setOnClickListener {
            viewModel.newAdministration(BodyPart.LEG_R)
        }
    }

    private fun updatePena(penDoses: Pair<Int, Int>) {
        binding.header.pen.setDoses(penDoses.first, penDoses.second)
        binding.header.remainingDoses.text = getString(R.string.remaining_doses, penDoses.second)
    }

    private fun updateBodyMap(dosesMap: Map<BodyPart, Boolean>) {
        binding.body.armLCheck.visibility =
            if (dosesMap[BodyPart.ARM_L] == true) View.VISIBLE else View.GONE
        binding.body.armRCheck.visibility =
            if (dosesMap[BodyPart.ARM_R] == true) View.VISIBLE else View.GONE
        binding.body.absLCheck.visibility =
            if (dosesMap[BodyPart.ABS_L] == true) View.VISIBLE else View.GONE
        binding.body.absRCheck.visibility =
            if (dosesMap[BodyPart.ABS_R] == true) View.VISIBLE else View.GONE
        binding.body.legLCheck.visibility =
            if (dosesMap[BodyPart.LEG_L] == true) View.VISIBLE else View.GONE
        binding.body.legRCheck.visibility =
            if (dosesMap[BodyPart.LEG_R] == true) View.VISIBLE else View.GONE
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
}