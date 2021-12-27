package com.dariomartin.easygrow.data.repository

import androidx.lifecycle.LiveData
import com.dariomartin.easygrow.data.model.Administration
import com.dariomartin.easygrow.data.model.HeightMeasure
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.data.model.Pen

interface IPatientRepository {
    fun getLivePatient(patientId: String? = null): LiveData<Patient>
    suspend fun updatePatient(patientId: String, patient: Patient)
    fun getAdministrations(patientId: String? = null): LiveData<List<Administration>>
    suspend fun addAdministration(administration: Administration)
    fun allPatients(): LiveData<List<Patient>>
    suspend fun addPen(patientId: String, newPen: Pen)
    fun getPens(patientId: String? = null): LiveData<List<Pen>>
    fun removePens(patientId: String)
    suspend fun updatePen(patientId: String? = null, pen: Pen)
    suspend fun removePen(patientId: String, pen: Pen)
    fun getUsedPens(patientId: String? = null): LiveData<List<Pen>>
    suspend fun addHeightMeasure(patientId: String, heightMeasure: HeightMeasure)
    fun getHeightMeasures(patientId: String? = null): LiveData<List<HeightMeasure>>
}