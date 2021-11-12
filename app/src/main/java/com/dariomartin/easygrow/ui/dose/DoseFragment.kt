package com.dariomartin.easygrow.ui.dose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.databinding.FragmentDoseBinding
import com.dariomartin.easygrow.model.Administration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoseFragment : Fragment() {

    private lateinit var viewModel: DosesViewModel
    private var _binding: FragmentDoseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this)[DosesViewModel::class.java]

        _binding = FragmentDoseBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.doses.observe(viewLifecycleOwner, { dosesMap ->
            updateBodyMap(dosesMap)
        })

        viewModel.penDoses.observe(viewLifecycleOwner, { penDoses ->
            updatePena(penDoses)
        })

        binding.body.armL.setOnClickListener {
            viewModel.newDose(Administration.BodyPart.ARM_L)
        }
        binding.body.armR.setOnClickListener {
            viewModel.newDose(Administration.BodyPart.ARM_R)
        }
        binding.body.absL.setOnClickListener {
            viewModel.newDose(Administration.BodyPart.ABS_L)
        }
        binding.body.absR.setOnClickListener {
            viewModel.newDose(Administration.BodyPart.ABS_R)
        }
        binding.body.legL.setOnClickListener {
            viewModel.newDose(Administration.BodyPart.LEG_L)
        }
        binding.body.legR.setOnClickListener {
            viewModel.newDose(Administration.BodyPart.LEG_R)
        }
    }

    private fun updatePena(penDoses: Pair<Int, Int>) {
        binding.header.pen.setDoses(penDoses.first, penDoses.second)
        binding.header.remainingDoses.text =getString(R.string.remaining_doses, penDoses.second)
    }

    private fun updateBodyMap(dosesMap: Map<Administration.BodyPart, Boolean>) {
        binding.body.armLCheck.visibility =
            if (dosesMap[Administration.BodyPart.ARM_L] == true) View.VISIBLE else View.GONE
        binding.body.armRCheck.visibility =
            if (dosesMap[Administration.BodyPart.ARM_R] == true) View.VISIBLE else View.GONE
        binding.body.absLCheck.visibility =
            if (dosesMap[Administration.BodyPart.ABS_L] == true) View.VISIBLE else View.GONE
        binding.body.absRCheck.visibility =
            if (dosesMap[Administration.BodyPart.ABS_R] == true) View.VISIBLE else View.GONE
        binding.body.legLCheck.visibility =
            if (dosesMap[Administration.BodyPart.LEG_L] == true) View.VISIBLE else View.GONE
        binding.body.legRCheck.visibility =
            if (dosesMap[Administration.BodyPart.LEG_R] == true) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}