package com.dariomartin.easygrow.data.repository

import androidx.lifecycle.LiveData
import com.dariomartin.easygrow.data.model.Administration
import com.dariomartin.easygrow.data.model.BodyPart
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.presentation.patient.profile.PatientForm
import java.util.*

interface IPatientRepository {
    suspend fun getPatient(): Patient?
    fun getLivePatient(patientId:String?): LiveData<Patient>
    suspend fun recordAdministration(newBodyPart: BodyPart, date: Calendar)
    suspend fun updatePatient(patientForm: PatientForm)
    suspend fun getAdministrations(): List<Administration>
    suspend fun addAdministration(administration: Administration)

}