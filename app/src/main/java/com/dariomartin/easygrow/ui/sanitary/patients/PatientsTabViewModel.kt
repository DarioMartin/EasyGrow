package com.dariomartin.easygrow.ui.sanitary.patients

import androidx.lifecycle.*
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.data.repository.IDoctorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientsTabViewModel @Inject constructor(private val doctorRepository: IDoctorRepository) :
    ViewModel() {

    val patients by lazy {
        val liveData = MutableLiveData<List<Patient>>()
        viewModelScope.launch {
            liveData.postValue(doctorRepository.getAssignedPatients())
        }
        return@lazy liveData
    }


}