package com.dariomartin.easygrow.presentation.patient.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariomartin.easygrow.data.model.Administration
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.data.repository.IPatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val patientRepository: IPatientRepository) :
    ViewModel() {

    val successfulUpdate: MutableLiveData<Boolean> = MutableLiveData(false)

    val administrations by lazy {
        val liveData = MutableLiveData<List<Administration>>()
        viewModelScope.launch {
            liveData.postValue(
                patientRepository.getAdministrations().sortedByDescending { it.date })
        }
        return@lazy liveData
    }


    fun getPatient(patientId: String? = null): LiveData<Patient> {
        return patientRepository.getLivePatient(patientId)
    }

    fun updatePatient(patientForm: PatientForm) {
        viewModelScope.launch {
            patientRepository.updatePatient(patientForm)
            successfulUpdate.postValue(true)
        }
    }

}