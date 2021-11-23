package com.dariomartin.easygrow.data.repository

import com.dariomartin.easygrow.data.mapper.Mapper
import com.dariomartin.easygrow.data.model.Administration
import com.dariomartin.easygrow.data.model.BodyPart
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.data.sources.firestore.FirestoreUserDataSource
import com.dariomartin.easygrow.data.sources.mock.PatientMockDataSource
import com.dariomartin.easygrow.ui.patient.profile.PatientForm
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor() : IPatientRepository {

    private val auth = FirebaseAuth.getInstance()

    private val firestore = FirestoreUserDataSource()
    private val mock = PatientMockDataSource()

    override suspend fun getPatient(): Patient? {
        return auth.currentUser?.uid?.let { uid ->
            firestore.getPatient(uid)?.let { dto ->
                Mapper.patientDtoMapper(dto, uid)
            }
        }
    }

    override suspend fun recordAdministration(newBodyPart: BodyPart, date: Calendar) {

    }

    override suspend fun updatePatient(patientForm: PatientForm) {
        getPatient()?.apply {
            this.name = patientForm.name
            this.surname = patientForm.surname
            this.height = patientForm.height
            this.weight = patientForm.weight
        }?.let {
            firestore.updatePatient(it.id, Mapper.patientMapper(it))
        }
    }

    override suspend fun getAdministrations(): List<Administration> {
        return auth.currentUser?.uid?.let { uid ->
            firestore.getAdministrations(uid)
                .map { dto -> Mapper.administrationDtoMapper(dto) }
        } ?: listOf()
    }

    override suspend fun addAdministration(administration: Administration) {
        auth.currentUser?.uid?.let {
            firestore.addAdminstration(
                it,
                Mapper.administrationMapper(administration)
            )
        }
    }
}