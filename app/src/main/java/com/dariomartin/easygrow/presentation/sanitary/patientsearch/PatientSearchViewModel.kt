package com.dariomartin.easygrow.presentation.sanitary.patientsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.data.repository.IPatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PatientSearchViewModel @Inject constructor(private val patientRepository: IPatientRepository) :
    ViewModel() {

    fun getPatients(): LiveData<List<Patient>> {
        return patientRepository.allPatients()
    }
}