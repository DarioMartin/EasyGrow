package com.dariomartin.easygrow.data.sources

import com.dariomartin.easygrow.data.dto.AdministrationDTO
import com.dariomartin.easygrow.data.dto.PatientDTO
import com.dariomartin.easygrow.data.model.User

interface IUserDataSource {
    suspend fun getType(userId: String): User.Type?
    suspend fun setType(userId: String, type: User.Type)
    suspend fun getPatient(userId: String): PatientDTO?
    suspend fun updatePatient(patientId: String, patient: PatientDTO)
    suspend fun getAdministrations(patientId: String): List<AdministrationDTO>
    suspend fun addAdminstration(patientId: String, administrationDTO: AdministrationDTO)

}