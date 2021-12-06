package com.dariomartin.easygrow.presentation.patient.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariomartin.easygrow.data.model.Administration
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.data.model.User
import com.dariomartin.easygrow.data.repository.IPatientRepository
import com.dariomartin.easygrow.data.repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val patientRepository: IPatientRepository,
    private val userRepository: IUserRepository
) :
    ViewModel() {

    val userType by lazy {
        val liveData = MutableLiveData<User.Type>()
        viewModelScope.launch {
            liveData.value = userRepository.getType()
        }
        return@lazy liveData
    }

    val successfulUpdate: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getAdministrations(patientId: String?): LiveData<List<Administration>> {
        return patientRepository.getAdministrations(patientId)
    }


    fun getPatient(patientId: String? = null): LiveData<Patient> {
        return patientRepository.getLivePatient(patientId)
    }

    fun updatePatient(patientId: String, patientForm: PatientForm) {
        viewModelScope.launch {
            patientRepository.updatePatient(patientId, patientForm)
            successfulUpdate.postValue(true)
        }
    }

}