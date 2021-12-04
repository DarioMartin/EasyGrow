package com.dariomartin.easygrow.presentation.sanitary.patientsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.data.repository.IDoctorRepository
import com.dariomartin.easygrow.data.repository.IPatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientSearchViewModel @Inject constructor(
    private val doctorRepository: IDoctorRepository,
    private val patientRepository: IPatientRepository
) :
    ViewModel() {

    fun getPatients(): LiveData<List<Patient>> {
        return patientRepository.allPatients()
    }

    fun assignPatient(patient: Patient) {
        viewModelScope.launch {
            doctorRepository.assignPatient(patient.id)
        }
    }
}