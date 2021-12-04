package com.dariomartin.easygrow.data.repository

import androidx.lifecycle.LiveData
import com.dariomartin.easygrow.data.model.Drug
import com.dariomartin.easygrow.data.model.Patient

interface IDoctorRepository {
    fun getAssignedPatients(): LiveData<MutableList<Patient>>
    suspend fun getPatient(): Patient?
    suspend fun getDrugs(): List<Drug>?
}