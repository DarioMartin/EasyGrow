package com.dariomartin.easygrow.data.repository

import com.dariomartin.easygrow.data.model.Administration
import com.dariomartin.easygrow.data.model.BodyPart
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.ui.patient.profile.PatientForm
import java.util.*

interface IPatientRepository {
    suspend fun getPatient(): Patient?
    suspend fun recordAdministration(newBodyPart: BodyPart, date: Calendar)
    suspend fun updatePatient(patientForm: PatientForm)
    suspend fun getAdministrations(): List<Administration>
    suspend fun addAdministration(administration: Administration)
}