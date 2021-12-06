package com.dariomartin.easygrow.presentation.patient.profile

import androidx.lifecycle.*
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

    private var patient: Patient? = null

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
        return patientRepository.getLivePatient(patientId).map { patient ->
            this.patient = patient
            patient
        }
    }

    fun updatePatient(patientId: String, patientForm: PatientForm) {
        viewModelScope.launch {
            patient?.apply {
                this.name = patientForm.name ?: ""
                this.surname = patientForm.surname ?: ""
                this.height = patientForm.height
                this.weight = patientForm.weight
                this.birthday = patientForm.birthday
            }?.let {
                patientRepository.updatePatient(patientId, it)
            }
            successfulUpdate.postValue(true)
        }
    }

}