package com.dariomartin.easygrow.ui.patient

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariomartin.easygrow.model.Administration
import com.dariomartin.easygrow.model.Patient
import com.dariomartin.easygrow.model.repository.IPatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val patientRepository: IPatientRepository) :
    ViewModel() {

    private val patient: MutableLiveData<Patient> =
        MutableLiveData<Patient>()

    private val doses: MutableLiveData<List<Administration>> =
        MutableLiveData<List<Administration>>()

    fun getPatient(): MutableLiveData<Patient> {
        viewModelScope.launch {
            patient.value = patientRepository.getPatient()
        }
        return patient
    }

    fun getDoses(): MutableLiveData<List<Administration>> {
        viewModelScope.launch {
            doses.value = patientRepository.getAdministrations().sortedByDescending { it.date }
        }
        return doses
    }
}