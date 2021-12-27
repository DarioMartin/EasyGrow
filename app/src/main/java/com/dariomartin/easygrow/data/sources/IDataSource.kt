package com.dariomartin.easygrow.data.sources

import androidx.lifecycle.LiveData
import com.dariomartin.easygrow.data.dto.*
import com.dariomartin.easygrow.data.model.User

interface IDataSource {
    suspend fun getType(userId: String): User.Type?
    suspend fun setType(userId: String, type: User.Type)


    suspend fun addDoctor(doctor: DoctorDTO)
    suspend fun removeDoctor(doctorId: String)
    suspend fun getDoctor(doctorId: String): DoctorDTO?
    suspend fun updateDoctor(doctor: DoctorDTO)


    suspend fun addAdministration(patientId: String, administration: AdministrationDTO)
    fun getAdministrations(patientId: String): LiveData<List<AdministrationDTO>>

    suspend fun assignPatientToDoctor(patientId: String, doctorId: String)
    suspend fun removePatientFromDoctor(patientId: String, doctorId: String)

    fun getDoctorPatients(doctorId: String): LiveData<List<PatientDTO>>

    fun getNotAssignedPatients(doctorId: String): LiveData<List<PatientDTO>>


    fun getLiveDoctor(doctorId: String): LiveData<DoctorDTO>

    /**
     * Patient Methods
     */

    suspend fun addPatient(patient: PatientDTO)
    suspend fun updatePatient(patient: PatientDTO)
    suspend fun removePatient(patientId: String)
    fun getPatient(patientId: String): LiveData<PatientDTO>
    fun getAllPatients(): LiveData<List<PatientDTO>>
    suspend fun addHeightMeasure(patientId: String, heightMeasure: HeightMeasureDTO)
    fun getPatientHeightMeasures(patientId: String): LiveData<List<HeightMeasureDTO>>

    /**
     * Drug Methods
     */

    suspend fun addDrug(drug: DrugDTO)
    suspend fun updateDrug(drug: DrugDTO)
    suspend fun removeDrug(drugId: String)
    fun getDrug(drugId: String): LiveData<DrugDTO>
    fun getDrugs(): LiveData<List<DrugDTO>>

    /**
     * Pen Methods
     */

    suspend fun addPen(patientId: String, pen: PenDTO)
    suspend fun updatePen(patientId: String, pen: PenDTO)
    suspend fun removePen(patientId: String, pen: PenDTO)
    fun removePens(patientId: String)
    fun getPens(patientId: String): LiveData<List<PenDTO>>
    fun getUsedPens(patientId: String): LiveData<List<PenDTO>>
}