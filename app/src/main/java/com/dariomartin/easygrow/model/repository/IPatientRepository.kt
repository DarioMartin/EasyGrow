package com.dariomartin.easygrow.model.repository

import com.dariomartin.easygrow.model.Administration
import com.dariomartin.easygrow.model.Patient
import java.util.*


interface IPatientRepository {
    suspend fun getPatient(): Patient
    suspend fun getAdministrations(): List<Administration>
    fun recordAdministration(newBodyPart: Administration.BodyPart, date: Calendar)
}