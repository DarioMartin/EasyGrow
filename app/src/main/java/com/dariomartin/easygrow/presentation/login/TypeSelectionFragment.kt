package com.dariomartin.easygrow.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dariomartin.easygrow.data.model.User
import com.dariomartin.easygrow.databinding.FragmentTypeSelectionBinding
import com.dariomartin.easygrow.presentation.patient.PatientActivity
import com.dariomartin.easygrow.presentation.sanitary.SanitaryActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TypeSelectionFragment : Fragment() {

    private lateinit var viewModel: TypeSelectionViewModel
    private var _binding: FragmentTypeSelectionBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[TypeSelectionViewModel::class.java]
        _binding = FragmentTypeSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.patientButton.setOnClickListener { viewModel.setUserType(User.Type.PATIENT) }
        binding.sanitaryButton.setOnClickListener { viewModel.setUserType(User.Type.SANITARY) }
        binding.logout.setOnClickListener { logout() }

        viewModel.userType.observe(viewLifecycleOwner, { userType ->
            userType?.let {
                when (it) {
                    User.Type.PATIENT -> goToPatient()
                    User.Type.SANITARY -> goToSanitary()
                }
            }
        })
    }

    private fun goToPatient() {
        val switchActivityIntent = Intent(requireContext(), PatientActivity::class.java)
        startActivity(switchActivityIntent)
        requireActivity().finish()
    }

    private fun goToSanitary() {
        val switchActivityIntent = Intent(requireContext(), SanitaryActivity::class.java)
        startActivity(switchActivityIntent)
        requireActivity().finish()
    }

    private fun logout() {
        viewModel.logout()
        val action =
            TypeSelectionFragmentDirections.actionNavigationTypeSelectionToNavigationLogin()
        findNavController().navigate(action)
    }
}