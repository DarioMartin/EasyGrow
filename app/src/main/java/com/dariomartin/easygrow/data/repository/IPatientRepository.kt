package com.dariomartin.easygrow.data.repository

import androidx.lifecycle.LiveData
import com.dariomartin.easygrow.data.model.Administration
import com.dariomartin.easygrow.data.model.BodyPart
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.data.model.Pen
import com.dariomartin.easygrow.presentation.patient.profile.PatientForm
import com.dariomartin.easygrow.presentation.sanitary.treatmentupdate.TreatmentForm
import java.util.*

interface IPatientRepository {
    suspend fun getPatient(patientId: String?): Patient?
    fun getLivePatient(patientId: String?): LiveData<Patient>
    suspend fun recordAdministration(newBodyPart: BodyPart, date: Calendar)
    suspend fun updatePatient(patientId: String, patient: Patient)
    fun getAdministrations(patientId: String?): LiveData<List<Administration>>
    suspend fun addAdministration(administration: Administration)
    fun allPatients(): LiveData<List<Patient>>
    suspend fun addPen(patientId: String, newPen: Pen)
    fun getPens(patientId: String?): LiveData<List<Pen>>
}