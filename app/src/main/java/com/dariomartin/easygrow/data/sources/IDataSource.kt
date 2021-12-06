package com.dariomartin.easygrow.data.sources

import androidx.lifecycle.LiveData
import com.dariomartin.easygrow.data.dto.*
import com.dariomartin.easygrow.data.model.User

interface IDataSource {
    suspend fun getType(userId: String): User.Type?
    suspend fun setType(userId: String, type: User.Type)

    suspend fun addPatient(patient: PatientDTO)
    suspend fun removePatient(patientId: String)
    suspend fun getPatient(patientId: String): PatientDTO?
    suspend fun updatePatient(patient: PatientDTO)


    suspend fun addDoctor(doctor: DoctorDTO)
    suspend fun removeDoctor(doctorId: String)
    suspend fun getDoctor(doctorId: String): DoctorDTO?
    suspend fun updateDoctor(doctor: DoctorDTO)

    suspend fun addDrug(drug: DrugDTO)
    suspend fun removeDrug(drugId: String)
    suspend fun getDrug(drugId: String): DrugDTO?
    suspend fun updateDrug(drug: DrugDTO)
    suspend fun addAdministration(patientId: String, administration: AdministrationDTO)
    fun getAdministrations(patientId: String): LiveData<List<AdministrationDTO>>

    suspend fun assignPatientToDoctor(patientId: String, doctorId: String)
    suspend fun removePatientFromDoctor(patientId: String, doctorId: String)

    fun getDoctorPatients(doctorId: String): LiveData<MutableList<PatientDTO>>

    fun getLivePatient(patientId: String): LiveData<PatientDTO>

    fun getLiveDoctor(doctorId: String): LiveData<DoctorDTO>

    fun getAllPatients(): LiveData<List<PatientDTO>>

    fun getLiveDrug(drugId: String): LiveData<DrugDTO>

    fun getDrugs(): LiveData<List<DrugDTO>>

    suspend fun removePen(patientId: String, penId: String)
    suspend fun getPen(patientId: String, penId: String): PenDTO?
    suspend fun updatePatient(patientId: String, pen: PenDTO)
    suspend fun addPen(patientId: String, pen: PenDTO)
    fun getPens(patientId: String): LiveData<List<PenDTO>>
}