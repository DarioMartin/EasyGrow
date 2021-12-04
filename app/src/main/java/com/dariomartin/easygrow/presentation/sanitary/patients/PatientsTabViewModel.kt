package com.dariomartin.easygrow.presentation.sanitary.patients

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.data.repository.IDoctorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientsTabViewModel @Inject constructor(private val doctorRepository: IDoctorRepository) :
    ViewModel() {

    fun getPatients(): LiveData<MutableList<Patient>> {
        return doctorRepository.getAssignedPatients()
    }

    fun removePatientFromDoctor(id: String) {
        viewModelScope.launch {
            doctorRepository.removePatientFromDoctor(id)
        }
    }
}