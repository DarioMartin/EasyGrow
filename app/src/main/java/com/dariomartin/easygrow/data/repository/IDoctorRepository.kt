package com.dariomartin.easygrow.data.repository

import androidx.lifecycle.LiveData
import com.dariomartin.easygrow.data.model.Patient

interface IDoctorRepository {
    fun getAssignedPatients(): LiveData<List<Patient>>
    suspend fun removePatientFromDoctor(patientId: String)
    suspend fun assignPatient(patientId: String)
    fun getNotAssignedPatients(): LiveData<List<Patient>>
}