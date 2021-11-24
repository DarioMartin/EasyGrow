package com.dariomartin.easygrow.data.repository

import com.dariomartin.easygrow.data.model.Drug
import com.dariomartin.easygrow.data.model.Patient

interface IDoctorRepository {
    suspend fun getAssignedPatients(): List<Patient>?
    suspend fun getPatient(): Patient?
    suspend fun getDrugs(): List<Drug>?
}