package com.dariomartin.easygrow.data.sources

import androidx.lifecycle.LiveData
import com.dariomartin.easygrow.data.dto.AdministrationDTO
import com.dariomartin.easygrow.data.dto.DoctorDTO
import com.dariomartin.easygrow.data.dto.DrugDTO
import com.dariomartin.easygrow.data.dto.PatientDTO
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
    suspend fun getDrugs(): List<DrugDTO>

    suspend fun addAdministration(patientId: String, administration: AdministrationDTO)
    suspend fun getAdministrations(patientId: String): List<AdministrationDTO>

    suspend fun assignPatientToDoctor(patientId: String, doctorId: String)
    suspend fun removePatientFromDoctor(patientId: String, doctorId: String)

    fun getDoctorPatients(doctorId: String): LiveData<MutableList<PatientDTO>>

    fun getLivePatient(patientId: String): LiveData<PatientDTO>

    fun getLiveDoctor(doctorId: String): LiveData<DoctorDTO>

}