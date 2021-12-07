package com.dariomartin.easygrow.presentation.patient.dose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.data.model.Administration
import com.dariomartin.easygrow.data.model.BodyPart
import com.dariomartin.easygrow.databinding.FragmentDoseBinding
import com.dariomartin.easygrow.presentation.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DoseFragment : BaseFragment<FragmentDoseBinding, DosesViewModel>() {

    private var lastAdministrations: List<Administration> = listOf()
    private var newAdministration: Administration? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLastNAdministrations(3).observe(viewLifecycleOwner, { administrations ->
            lastAdministrations = administrations
            updateBodyMap()
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
            newAdministration(BodyPart.ARM_L)
        }
        binding.body.armR.setOnClickListener {
            newAdministration(BodyPart.ARM_R)
        }
        binding.body.absL.setOnClickListener {
            newAdministration(BodyPart.ABS_L)
        }
        binding.body.absR.setOnClickListener {
            newAdministration(BodyPart.ABS_R)
        }
        binding.body.legL.setOnClickListener {
            newAdministration(BodyPart.LEG_L)
        }
        binding.body.legR.setOnClickListener {
            newAdministration(BodyPart.LEG_R)
        }
    }

    private fun updatePena(penDoses: Pair<Int, Int>) {
        binding.header.pen.setDoses(penDoses.first, penDoses.second)
        binding.header.remainingDoses.text = getString(R.string.remaining_doses, penDoses.second)
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
}